package com.sparta.company.dto.request;

import java.util.UUID;

public record CompanyUpdateRequest(
        String companyName,
        String address,
        UUID hubId,
        UUID userId,
        UUID productId
) {}
