package com.sparta.company.dto.request;

import com.sparta.company.entity.CompanyType;

import java.util.UUID;

public record CompanyCreateRequest(
        String companyName,
        CompanyType companyType,
        String address,
        UUID userId
) {}
