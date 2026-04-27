package com.ecommercebackoffice.customer.repository;

import com.ecommercebackoffice.customer.entity.Customer;
import com.ecommercebackoffice.customer.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);
    Optional<Customer> findByIdAndDeletedAtIsNull(Long id);


    /**
     * 고객 리스트 조회를 위한 쿼리
     * @param keyword
     * @param status
     * @param pageable
     * @return
     */
    @Query("SELECT c FROM Customer c " +
            "WHERE c.deletedAt IS NULL " +
            "AND (:keyword IS NULL OR c.name LIKE %:keyword% OR c.email LIKE %:keyword%) " +
            "AND (:status IS NULL OR c.status = :status)")
    Page<Customer> findAllWithFilter(
            @Param("keyword") String keyword,
            @Param("status") CustomerStatus status,
            Pageable pageable
    );
}
