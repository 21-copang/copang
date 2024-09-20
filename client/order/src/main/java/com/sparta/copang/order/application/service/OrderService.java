package com.sparta.copang.order.application.service;

import com.sparta.copang.order.application.dto.company.CompanyResponse;
import com.sparta.copang.order.application.dto.delivery.CreateDeliveryRequest;
import com.sparta.copang.order.application.dto.delivery.DeliveryResponse;
import com.sparta.copang.order.application.dto.hub.HubResponse;
import com.sparta.copang.order.application.dto.order.CreateOrderRequest;
import com.sparta.copang.order.application.dto.order.OrderResponse;
import com.sparta.copang.order.application.dto.order.UpdateOrderRequest;
import com.sparta.copang.order.application.dto.product.ProductResponse;
import com.sparta.copang.order.common.exception.ApplicationException;
import com.sparta.copang.order.common.status.OrderErrorCode;
import com.sparta.copang.order.domain.entity.Order;
import com.sparta.copang.order.domain.repository.OrderRepository;
import com.sparta.copang.order.infrastructure.client.*;
import com.sparta.copang.order.presentation.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CompanyClient companyClient;
    private final HubClient hubClient;
    private final ProductClient productClient;
    private final DeliveryClient deliveryClient;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest requestDto) {
        UUID startHubId = getHubId(requestDto.orderCompanyId());
        UUID endHubId = getHubId(requestDto.receiveCompanyId());
        UUID hubManagerId = getHubManagerId(startHubId);
        requestDto.productList().forEach(productRequestData -> validProduct(productRequestData.productId()));

        Order order = saveOrder(requestDto, hubManagerId);
        createDelivery(order, requestDto, startHubId, endHubId);

        return OrderResponse.of(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(UUID orderId) {
        Order order = findById(orderId);
        return OrderResponse.of(order);
    }

    @Transactional
    public OrderResponse updateOrder(UUID orderId, UpdateOrderRequest requestDto) {
        requestDto.productList().forEach(productRequestData -> validProduct(productRequestData.productId()));

        Order order = findById(orderId);
        order.updateOrderProduct(requestDto.productList());

        return OrderResponse.of(order);
    }

    @Transactional
    public OrderResponse cancelOrder(UUID orderId) {
        Order order = findById(orderId);
        order.updateCanceled(true);
        orderRepository.save(order);

        deleteDelivery(order.getDeliveryId());

        return OrderResponse.of(order);
    }

    @Transactional
    public void deleteOrder(UUID orderId, UUID uuid) {
        Order order = findById(orderId);
        order.setDeletedYnTrue(uuid);
        orderRepository.save(order);

        deleteDelivery(order.getDeliveryId());
    }

    @Transactional
    public Order saveOrder(CreateOrderRequest requestDto, UUID hubManagerId) {
        Order order = Order.create(requestDto, hubManagerId);
        Order savedOrder = orderRepository.save(order);
        savedOrder.updateOrderProduct(requestDto.productList());
        return savedOrder;
    }

    @Transactional
    public Order findById(UUID orderId) {
        return orderRepository.findByIdAndDeletedYn(orderId, false)
                .orElseThrow(() -> new ApplicationException(OrderErrorCode.ORDER_NOT_FOUND));
    }

    @Transactional
    public UUID getHubId(UUID companyId){
        try {
            ApiResponse<CompanyResponse> response = companyClient.getCompany(companyId);
            return response.data().hubId();
        } catch (Exception e) {
            throw new ApplicationException(OrderErrorCode.GET_COMPANY_ERROR);
        }
    }

    @Transactional
    public UUID getHubManagerId(UUID hubId){
        try {
            CommonResponse<HubResponse> response = hubClient.getHubId(hubId);
            return  response.body().hub_manager();
        } catch (Exception e) {
            throw new ApplicationException(OrderErrorCode.GET_HUB_ERROR);
        }
    }

    @Transactional
    public void validProduct(UUID productId){
        try {
            ApiResponse<ProductResponse> response = productClient.getProduct(productId);
        } catch (Exception e) {
            throw new ApplicationException(OrderErrorCode.GET_PRODUCT_ERROR);
        }
    }

    @Transactional
    public void createDelivery(Order order, CreateOrderRequest requestDto, UUID startHubId, UUID endHubId) {
        CreateDeliveryRequest createDeliveryRequest = new CreateDeliveryRequest(
                requestDto.receiver(), requestDto.slackEmail(), requestDto.address(),
                order.getId(), startHubId, endHubId
        );

        try {
            CommonResponse<DeliveryResponse> response = deliveryClient.createDelivery(createDeliveryRequest);
            order.updateDeliveryId(response.body().id());
        } catch (Exception e) {
            throw new ApplicationException(OrderErrorCode.CREATE_DELIVERY_ERROR);
        }
    }

    @Transactional
    public void deleteDelivery(UUID deliveryId) {
        try {
            deliveryClient.deleteDelivery(deliveryId);
        } catch (Exception e) {
            throw new ApplicationException(OrderErrorCode.DELETE_DELIVERY_ERROR);
        }
    }
}
