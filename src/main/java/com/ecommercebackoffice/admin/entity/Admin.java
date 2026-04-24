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

@SQLDelete(sql = "UPDATE orders SET deletedAt = NOW() WHERE id = ?")
@SQLRestriction("deletedAt IS NULL")
@Entity
@Getter
@Table(name="admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    private LocalDateTime approvedAt;
    private LocalDateTime deletedAt;

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
}
