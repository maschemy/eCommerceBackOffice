package com.customer.entity;

import com.ecommercebackoffice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table (name = "customers")
@NoArgsConstructor
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;

    @Column
    private LocalDateTime deletedAt;


    /**
     * 고객 엔티티 생성자.
     * 신규 고객 등록 시 사용하며, 초기 상태는 호출부에서 지정
     * @param name              고객 이름
     * @param email             고객 이메일(중복 불가)
     * @param phoneNumber       고객 전화번호
     * @param status            고객 상태{@link CustomerStatus}
     */
    public Customer(String name, String email, String phoneNumber, CustomerStatus status) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    /**
     * 고객 기본 정보 수정
     * 이름, 이메일, 전화번호만 변경 가능, 전달된 값이 null이 아닌 경우에만 해당 필드 업데이트
     * 호출전 서비스 레이어에서 유효성 검증 완료해야 한다.
     * @param name
     * @param email
     * @param phoneNumber
     */
    public void update(String name, String email, String phoneNumber){
        if(name != null) this.name = name;
        if(email != null) this.email = email;
        if(phoneNumber != null) this.phoneNumber = phoneNumber;
    }

    /**
     * 고객을 soft delete 처리한다.
     * 실제 행을 삭제하지 않고 {@code deletedAt} 필드에 현재 시각을 기록한다.
     */
    public void delete(){
        this.deletedAt = LocalDateTime.now();
    }

    /**
     * 고객 상태 변경 {@link CustomerStatus#ACTIVE}, {@link CustomerStatus#INACTIVE}, {@link CustomerStatus#SUSPENDED}
     * @param status
     */
    public void updateStatus(CustomerStatus status){
        this.status = status;
    }
}
