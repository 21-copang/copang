package com.sparta.copang.order.domain.entity;

import com.sparta.copang.order.application.dto.order.CreateOrderRequest;
import com.sparta.copang.order.application.dto.order.ProductRequestData;
import com.sparta.copang.order.domain.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;
    private Boolean canceled;
    private UUID orderCompanyId;
    private UUID receiveCompanyId;
    private UUID deliveryId;
    private UUID hubManagerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<OrderProduct> orderProducts;

    public static Order create(CreateOrderRequest requestDto, UUID hubManagerId) {
        return Order.builder()
                .canceled(false)
                .orderCompanyId(requestDto.orderCompanyId())
                .receiveCompanyId(requestDto.receiveCompanyId())
                .deliveryId(null)
                .hubManagerId(hubManagerId)
                .orderProducts(new ArrayList<>())
                .build();
    }

    public void updateDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
    }

    public void updateCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public void updateOrderProduct(List<ProductRequestData> productRequestData) {
        this.orderProducts = productRequestData.stream().map(OrderProduct::create).toList();
    }
}
