package com.sparta.product.dto.request;

import java.util.UUID;

public record ProductUpdateRequest(
        String productName,
        UUID companyId,
        UUID hubId
) {}
