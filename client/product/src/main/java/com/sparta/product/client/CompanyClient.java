package com.sparta.product.client;

import com.sparta.product.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyClient {

    @GetMapping("/api/companies/{companyId}")
    ApiResponse<CompanyResponse> getCompanyByCompanyId(@PathVariable UUID companyId);

}
