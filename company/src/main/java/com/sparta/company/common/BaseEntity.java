package com.sparta.company.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    private String deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    private Boolean deletedYn = false;

    public void setDeletedYnTrue(String username) {
        this.deletedYn = true;
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = username;
    }
    public void setDeletedYnFalse() {
        this.deletedYn = false;
        this.deletedAt = null;
        this.deletedBy = null;
    }

    public Boolean isDeleted() {
        return this.deletedYn;
    }

    public void setUpdatedBy(String username) {
        this.updatedBy = username;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCreatedBy(String username) {
        this.createdBy = username;
        this.createdAt = LocalDateTime.now();
    }

}
