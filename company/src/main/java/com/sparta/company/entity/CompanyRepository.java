package com.sparta.company.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    // 회사명으로 검색 (일부 문자열이 포함된 검색)
    Page<Company> findByCompanyNameContaining(String companyName, Pageable pageable);

}
