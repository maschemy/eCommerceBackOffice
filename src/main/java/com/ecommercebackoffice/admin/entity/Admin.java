package com.ecommercebackoffice.admin.entity;

import com.ecommercebackoffice.admin.enums.AdminRole;
import com.ecommercebackoffice.admin.enums.AdminStatus;
import com.ecommercebackoffice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@SQLDelete(sql = "UPDATE orders SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Entity
@Getter
@Table(name="admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "reject")
    private String reject;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true , nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminStatus status;

    public Admin(String name,String email , String password , String phoneNumber ,AdminRole role)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = AdminStatus.PENDING;
    }

    public void adminUpdate(String name , String email, String phoneNumber)
    {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void roleUpdate(AdminRole role) {
        this.role = role;
        this.approvedAt = LocalDateTime.now();
    }

    public void statusUpdate(AdminStatus status)
    {
        this.status = status;
        this.approvedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void passwordChange(String changedPassword)
    {
        this.password = changedPassword;
    }

    public void approve() {
        if (this.status != AdminStatus.PENDING) {
            throw new IllegalStateException("승인대기 상태의 관리자만 승인할 수 있습니다.");
        }

        this.status = AdminStatus.ACTIVE;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject(String reject)
    {
        if (this.status != AdminStatus.PENDING) {
            throw new IllegalStateException("승인대기 상태의 관리자만 거부할 수 있습니다.");
        }

        this.status = AdminStatus.REJECTED;
        this.rejectedAt = LocalDateTime.now();
        this.reject = reject;
    }
}
