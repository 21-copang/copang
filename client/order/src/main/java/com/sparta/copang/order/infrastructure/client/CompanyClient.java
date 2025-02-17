package com.sparta.copang.order.infrastructure.client;

import com.sparta.copang.order.application.dto.company.CompanyResponse;
import com.sparta.copang.order.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "COMPANY-SERVICE", configuration = FeignConfig.class)
public interface CompanyClient {

    @GetMapping("/api/companies/{companyId}")
    ApiResponse<CompanyResponse> getCompany(@PathVariable UUID companyId);
}
