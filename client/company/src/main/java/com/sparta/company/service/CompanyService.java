package com.sparta.company.service;

import com.sparta.company.client.UserClient;
import com.sparta.company.client.UserResponse;
import com.sparta.company.common.ApiResponse;
import com.sparta.company.dto.request.CompanyCreateRequest;
import com.sparta.company.dto.request.CompanyUpdateRequest;
import com.sparta.company.dto.request.CompanyTypeUpdateRequest;
import com.sparta.company.dto.response.CompanyCreateResponse;
import com.sparta.company.dto.response.CompanyResponse;
import com.sparta.company.dto.response.CompanyUpdateResponse;
import com.sparta.company.entity.Company;
import com.sparta.company.entity.CompanyRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Slf4j
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserClient userClient;

    public CompanyService(CompanyRepository companyRepository, UserClient userClient) {
        this.companyRepository = companyRepository;
        this.userClient = userClient;
    }

    // 업체 등록
    public CompanyCreateResponse createCompany(CompanyCreateRequest request, UUID currentUserId, String currentUserRole) {
        UserResponse userResponse = null;
        try {
            // Feign Client로부터 ApiResponse<UserResponse> 객체 받기
            ApiResponse<UserResponse> userApiResponse = userClient.getUserById(request.userId(), currentUserId, currentUserRole);

            // 사용자 데이터 추출
            userResponse = userApiResponse.getData();

            // 사용자 유효성 검증
            if (userResponse == null || userResponse.getId() == null) {
                throw new IllegalArgumentException("유효한 사용자 ID가 아닙니다.");
            }
        } catch (FeignException e) {
            log.error("Error fetching user: {}", e.getMessage());
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. 유효한 사용자 ID를 입력하세요.");
        }

        // 받은 사용자 ID로 Company 생성
        UUID userId = userResponse.getId();
        Company company = new Company(
                request.companyName(),
                request.companyType(),
                request.address(),
                userId
        );

        // Company 저장
        companyRepository.save(company);
        return new CompanyCreateResponse(company.getCompanyId(), company.getCompanyName(), "업체가 성공적으로 등록되었습니다.");
    }

    // 업체 정보 수정
    public CompanyUpdateResponse updateCompany(UUID companyId, CompanyUpdateRequest request, UUID currentUserId, String currentUserRole) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        // 사용자 정보 조회
        UserResponse userResponse;
        try {
            log.info("Fetching user info for update, ID = {}, with headers X-User-Id = {}, X-User-Role = {}",
                    request.userId(), currentUserId, currentUserRole);

            // Feign Client를 통해 UserResponse 객체 받기
            ApiResponse<UserResponse> userApiResponse = userClient.getUserById(request.userId(), currentUserId, currentUserRole);
            userResponse = userApiResponse.getData();

            log.info("UserResponse received: ID = {}, Username = {}, Email = {}",
                    userResponse.getId(), userResponse.getUsername(), userResponse.getEmail());

            // 사용자 유효성 검증
            if (userResponse == null || userResponse.getId() == null) {
                throw new IllegalArgumentException("유효한 사용자 ID가 아닙니다.");
            }
        } catch (FeignException e) {
            log.error("Error fetching user: {}", e.getMessage());
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. 유효한 사용자 ID를 입력하세요.");
        }

        // 받은 사용자 ID로 Company 업데이트
        UUID userId = userResponse.getId();
        if (request.companyName() != null) {
            company.setCompanyName(request.companyName());
        }
        if (request.address() != null) {
            company.setAddress(request.address());
        }
        company.setUserId(userId);

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
                company.getUserId()
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
                company.getUserId()
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
                company.getUserId()
        ));
    }

    // 업체 삭제
    public void deleteCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
        companyRepository.delete(company);
    }
}
