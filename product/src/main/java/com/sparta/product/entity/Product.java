package com.sparta.product.entity;

import com.sparta.product.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "p_products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID productId;
    private String productName;
    private UUID orderProductsId;
    private UUID hubId;

    public Product(String productName, UUID orderProductsId, UUID hubId) {
        this.productId = UUID.randomUUID();
        this.productName = productName;
        this.orderProductsId = orderProductsId;
        this.hubId = hubId;
    }
}
