package com.sparta.copang.order.application.dto.product;

import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String productName,
        UUID orderProductsId,
        UUID hubId
) {}