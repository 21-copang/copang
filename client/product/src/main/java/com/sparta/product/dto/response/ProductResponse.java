package com.sparta.product.dto.response;

import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String productName,
        UUID companyId,
        UUID hubId
) {}
