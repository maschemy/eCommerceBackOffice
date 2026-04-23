package com.ecommercebackoffice.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity { //외부 클래스에서 사용할수없도록

    @CreatedDate //생성시간
    @Column(updatable = false)
//    @Temporal(TemporalType.TIMESTAMP) 생략이 가능하다
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 수정된 시간
//    @Temporal(TemporalType.TIMESTAMP) 생략가능
    private LocalDateTime modifiedAt;
}