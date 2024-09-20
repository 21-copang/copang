package com.sparta.company.dto.response;

import com.sparta.company.entity.CompanyType;

import java.util.UUID;

public record CompanyResponse(
        UUID companyId,
        String companyName,
        CompanyType companyType,
        String address,
        UUID userId,
        UUID hubId
) {}
