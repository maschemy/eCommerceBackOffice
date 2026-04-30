package com.ecommercebackoffice.admin.repository;

import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.admin.enums.AdminStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM admins WHERE email = :email", nativeQuery = true)
    long countByEmailIncludeDeleted(@Param("email") String email);

    @Query("""
    SELECT a
    FROM Admin a
    WHERE (:keyword IS NULL
        OR :keyword = ''
        OR a.name LIKE CONCAT('%', :keyword, '%')
        OR a.email LIKE CONCAT('%', :keyword, '%'))
""")
    Page<Admin> searchNameOrEmail(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    long countByDeletedAtIsNull();
    long countByStatusAndDeletedAtIsNull(AdminStatus status);
}


