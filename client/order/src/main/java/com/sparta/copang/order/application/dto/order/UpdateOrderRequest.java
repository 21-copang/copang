package com.sparta.copang.order.application.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateOrderRequest (
        @Valid
        @Size(min = 1, message = "ProductList is empty")
        List<ProductRequestData> productList

) {
}
