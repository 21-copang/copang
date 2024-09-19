package com.sparta.product.client;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CompanyResponse {
    private UUID companyId;
    private String companyName;
    private String companyType;
    private String address;
    private UUID userId;
}

