package com.sparta.company.dto.response;

import java.util.UUID;

public record CompanyCreateResponse(
        UUID companyId,
        String companyName,
        String message
) {}
