package com.sparta.copang.order.application.dto.company;

import java.util.UUID;

public record CompanyResponse(
        UUID companyId,
        String companyName,
        CompanyType companyType,
        String address,
        UUID hubId,
        UUID userId
) {}