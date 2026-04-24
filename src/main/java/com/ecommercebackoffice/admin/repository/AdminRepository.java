package com.ecommercebackoffice.admin.repository;

import com.ecommercebackoffice.admin.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);
    Boolean existsByEmail(String email);
    Page<Admin> findByNameContainingOrEmailContaining(String name, String email, Pageable pageable);
    //부분검색을 위한 Containing ex) name = 홍길동 , name = 홍 만해도 검색가능
}
