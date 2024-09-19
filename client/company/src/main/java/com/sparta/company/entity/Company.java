package com.sparta.company.entity;

import com.sparta.company.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "p_companies")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID companyId;

    @Column(nullable = false)
    private String companyName;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    private String address;

    private UUID userId;

    public Company(String companyName, CompanyType companyType, String address, UUID userId) {
        this.companyId = UUID.randomUUID();
        this.companyName = companyName;
        this.companyType = companyType;
        this.address = address;
        this.userId = userId;
    }
}
