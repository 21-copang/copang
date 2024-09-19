package com.sparta.copang.order.application.dto.order;

import com.sparta.copang.order.domain.entity.Order;
import com.sparta.copang.order.domain.entity.OrderProduct;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
public record OrderResponse(
        UUID orderId,
        Boolean canceled,
        UUID orderCompanyId,
        UUID receiveCompanyId,
        UUID deliveryId,
        UUID hubManagerId,
        List<ProductResponseData> productList
) {

    @Builder(access = AccessLevel.PRIVATE)
    public record ProductResponseData (
            UUID productId,
            Integer quantity
    ) {
        public static ProductResponseData of (OrderProduct orderProduct) {
            return ProductResponseData.builder()
                    .productId(orderProduct.getProductId())
                    .quantity(orderProduct.getQuantity())
                    .build();
        }
    }

    public static OrderResponse of(Order order) {
        List<ProductResponseData> productList =
                order.getOrderProducts().stream().map(ProductResponseData::of).toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .canceled(order.getCanceled())
                .orderCompanyId(order.getOrderCompanyId())
                .receiveCompanyId(order.getReceiveCompanyId())
                .deliveryId(order.getDeliveryId())
                .hubManagerId(order.getHubManagerId())
                .productList(productList)
                .build();
    }
}
