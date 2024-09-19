package com.sparta.company.dto.response;

import java.util.UUID;

public record CompanyUpdateResponse(
        UUID companyId,
        String companyName,
        String message
) {}
