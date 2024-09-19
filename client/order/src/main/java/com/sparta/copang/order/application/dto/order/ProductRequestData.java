package com.sparta.copang.order.application.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductRequestData(
        @NotNull(message = "ProductId is null")
        UUID productId,

        @Min(value = 1, message = "Product quantity is less than 1")
        Integer quantity
) { }
