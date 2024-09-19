package com.sparta.company.controller;

import com.sparta.company.common.ApiResponse;
import com.sparta.company.dto.request.CompanyCreateRequest;
import com.sparta.company.dto.request.CompanyUpdateRequest;
import com.sparta.company.dto.request.CompanyTypeUpdateRequest;
import com.sparta.company.dto.response.CompanyCreateResponse;
import com.sparta.company.dto.response.CompanyResponse;
import com.sparta.company.service.CompanyService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // 업체 등록
    @PostMapping("")
    public ApiResponse<?> createCompany(@RequestBody CompanyCreateRequest companyCreateRequest) {
        CompanyCreateResponse response = companyService.createCompany(companyCreateRequest);
        return ApiResponse.success("OK", response, "업체 등록 성공");
    }

    // 업체 정보 수정
    @PutMapping("/{companyId}")
    public ApiResponse<?> updateCompany(@PathVariable UUID companyId, @RequestBody CompanyUpdateRequest companyUpdateRequest) {
        companyService.updateCompany(companyId, companyUpdateRequest);
        return ApiResponse.success("OK", companyId, "업체 정보 수정 성공");
    }

    // 업체 타입 변경
    @PutMapping("/type/{companyId}")
    public ApiResponse<?> updateCompanyType(@PathVariable UUID companyId, @RequestBody CompanyTypeUpdateRequest companyTypeUpdateRequest) {
        companyService.updateCompanyType(companyId, companyTypeUpdateRequest);
        return ApiResponse.success("OK", companyId, "업체 타입 변경 성공");
    }

    // 업체 상세 조회
    @GetMapping("/{companyId}")
    public ApiResponse<?> getCompany(@PathVariable UUID companyId) {
        CompanyResponse response = companyService.getCompany(companyId);
        return ApiResponse.success("OK", response, "업체 조회 성공");
    }

    // 모든 업체 조회
    @GetMapping("")
    public ApiResponse<?> getAllCompanies(Pageable pageable) {
        return ApiResponse.success("OK", companyService.getAllCompanies(pageable), "업체 목록 조회 성공");
    }

    // 업체 검색 (업체명 기준)
    @GetMapping("/search")
    public ApiResponse<?> searchCompany(@RequestParam(value = "companyName", required = true) String companyName, Pageable pageable) {
        return ApiResponse.success("OK", companyService.searchCompany(companyName, pageable), "업체 검색 성공");
    }

    // 업체 삭제
    @DeleteMapping("/{companyId}")
    public ApiResponse<?> deleteCompany(@PathVariable UUID companyId) {
        companyService.deleteCompany(companyId);
        return ApiResponse.success("OK", companyId, "업체 삭제 성공");
    }
}
