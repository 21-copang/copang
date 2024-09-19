package com.sparta.company.service;

import com.sparta.company.client.HubClient;
import com.sparta.company.client.ProductClient;
import com.sparta.company.client.UserClient;
import com.sparta.company.dto.request.CompanyCreateRequest;
import com.sparta.company.dto.request.CompanyUpdateRequest;
import com.sparta.company.dto.request.CompanyTypeUpdateRequest;
import com.sparta.company.dto.response.CompanyCreateResponse;
import com.sparta.company.dto.response.CompanyResponse;
import com.sparta.company.dto.response.CompanyUpdateResponse;
import com.sparta.company.entity.Company;
import com.sparta.company.entity.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final HubClient hubClient;
    private final UserClient userClient;
    private final ProductClient productClient;  // ProductClient 추가

    public CompanyService(CompanyRepository companyRepository, HubClient hubClient, UserClient userClient, ProductClient productClient) {
        this.companyRepository = companyRepository;
        this.hubClient = hubClient;
        this.userClient = userClient;
        this.productClient = productClient;
    }

    // 업체 등록
    public CompanyCreateResponse createCompany(CompanyCreateRequest request) {
        UUID hubId = hubClient.getHubByHubId(request.hubId());
        UUID userId = userClient.getUserByUserId(request.userId());
        UUID productId = productClient.getProductByProductId(request.productId());

        Company company = new Company(
                request.companyName(),
                request.companyType(),
                request.address(),
                hubId,
                userId,
                productId
        );

        companyRepository.save(company);
        return new CompanyCreateResponse(
                company.getCompanyId(),
                company.getCompanyName(),
                "업체가 성공적으로 등록되었습니다."
        );
    }

    // 업체 정보 수정
    public CompanyUpdateResponse updateCompany(UUID companyId, CompanyUpdateRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        UUID hubId = hubClient.getHubByHubId(request.hubId());
        UUID userId = userClient.getUserByUserId(request.userId());
        UUID productId = productClient.getProductByProductId(request.productId());

        if (request.companyName() != null) {
            company.setCompanyName(request.companyName());
        }
        if (request.address() != null) {
            company.setAddress(request.address());
        }
        company.setHubId(hubId);
        company.setUserId(userId);
        company.setProductId(productId);

        companyRepository.save(company);

        return new CompanyUpdateResponse(
                company.getCompanyId(),
                company.getCompanyName(),
                "업체 정보가 성공적으로 수정되었습니다."
        );
    }

    // 업체 타입 변경
    public void updateCompanyType(UUID companyId, CompanyTypeUpdateRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        company.setCompanyType(request.companyType());
        companyRepository.save(company);
    }

    // 업체 상세 조회
    public CompanyResponse getCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
        return new CompanyResponse(
                company.getCompanyId(),
                company.getCompanyName(),
                company.getCompanyType(),
                company.getAddress(),
                company.getHubId(),
                company.getUserId(),
                company.getProductId()
        );
    }

    // 모든 업체 조회
    public Page<CompanyResponse> getAllCompanies(Pageable pageable) {
        Page<Company> companies = companyRepository.findAll(pageable);
        return companies.map(company -> new CompanyResponse(
                company.getCompanyId(),
                company.getCompanyName(),
                company.getCompanyType(),
                company.getAddress(),
                company.getHubId(),
                company.getUserId(),
                company.getProductId()
        ));
    }

    // 업체 검색
    public Page<CompanyResponse> searchCompany(String companyName, Pageable pageable) {
        Page<Company> companies = companyRepository.findByCompanyNameContaining(companyName, pageable);
        return companies.map(company -> new CompanyResponse(
                company.getCompanyId(),
                company.getCompanyName(),
                company.getCompanyType(),
                company.getAddress(),
                company.getHubId(),
                company.getUserId(),
                company.getProductId()
        ));
    }

    // 업체 삭제
    public void deleteCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
        companyRepository.delete(company);
    }
}
