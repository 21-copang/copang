package com.sparta.copang.order.domain.entity;

import com.sparta.copang.order.application.dto.order.CreateOrderRequest;
import com.sparta.copang.order.application.dto.order.ProductRequestData;
import com.sparta.copang.order.domain.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_order_products")
public class OrderProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_product_id")
    private UUID id;
    private Integer quantity;
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public static OrderProduct create(ProductRequestData productData) {
        return OrderProduct.builder()
                .quantity(productData.quantity())
                .productId(productData.productId())
                .build();
    }
}
