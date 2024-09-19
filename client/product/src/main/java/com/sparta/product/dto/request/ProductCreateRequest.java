package com.sparta.product.dto.request;

import java.util.UUID;

public record ProductCreateRequest(
        String productName,
        UUID companyId,
        UUID hubId
) {}
