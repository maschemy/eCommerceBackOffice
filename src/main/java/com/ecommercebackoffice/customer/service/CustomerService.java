package com.ecommercebackoffice.customer.service;

import com.ecommercebackoffice.common.exception.NotFoundException;
import com.ecommercebackoffice.customer.dto.*;
import com.ecommercebackoffice.customer.entity.Customer;
import com.ecommercebackoffice.customer.entity.CustomerStatus;
import com.ecommercebackoffice.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * 고객 목록 조회
     * 탈퇴 고객 제외, 키워드(이름,이메일) 검색, 상태 필터, 페이징,정렬
     * @param keyword
     * @param status
     * @param page
     * @param size
     * @param sortBy
     * @param direction
     * @return
     */
    @Transactional(readOnly = true)
    public Page<SearchCustomerResponseDto> findAll(
            String keyword, CustomerStatus status, int page,
            int size, String sortBy, String direction
    ) {
        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortDirection, sortBy));

        Page<Object[]> customers = customerRepository.findAllWithFilter(keyword, status, pageable);

        return customers.map(row -> {
            Customer customer = (Customer) row[0];
            Long totalOrderCount = ((Number) row[1]).longValue(); // row[1]를 Number로 캐시틍-> .longValue()로 Long으로 변환
            Long totalOrderAmount = ((Number) row[2]).longValue();

            return new SearchCustomerResponseDto(
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhoneNumber(),
                    customer.getStatus(),
                    customer.getCreatedAt(),
                    customer.getModifiedAt(),
                    totalOrderCount,
                    totalOrderAmount
            );
        });
    }

    /**
     * 고객 단건 조회
     * @param customerId
     * @return
     */
    @Transactional(readOnly = true)
    public SearchCustomerResponseDto findOne(Long customerId) {
        List<Object[]> results = customerRepository.findOneWithOrderStats(customerId);

        if (results.isEmpty()) {
            throw new NotFoundException("고객 id가 존재하지 않습니다.");
        }

        Object[] row = results.get(0);

        Customer customer = (Customer) row[0];
        Long totalOrderCount = ((Number) row[1]).longValue();
        Long totalOrderAmount = ((Number) row[2]).longValue();

        return new SearchCustomerResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getModifiedAt(),
                totalOrderCount,
                totalOrderAmount
        );
    }

    /**
     * 고객 정보 수정
     * @param request
     * @param customerId
     * @return
     */
    @Transactional
    public UpdateCustomerResponseDto updateInfo(@Valid UpdateCustomerRequestDto request, Long customerId) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId).orElseThrow(
                () -> new NotFoundException("고객 id가 존재하지 않습니다.")
        );

        customer.update(request.getName(), request.getEmail(), request.getPhoneNumber());


        return new UpdateCustomerResponseDto(
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getModifiedAt()
        );
    }

    /**
     * 고객 상태 변경
     * @param request
     * @param customerId
     * @return
     */
    @Transactional
    public UpdateCustomerStatusResponseDto updateStatus(@Valid UpdateCustomerStatusRequestDto request,Long customerId) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId).orElseThrow(
                () -> new NotFoundException("고객 id가 존재하지 않습니다.")
        );

        customer.updateStatus(request.getStatus());

        return new UpdateCustomerStatusResponseDto(
                customer.getId(),
                customer.getStatus(),
                customer.getModifiedAt()
        );
    }

    /**
     * 고객 삭제(soft delete)
     * @param customerId
     */
    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new NotFoundException("고객 id가 존재하지 않습니다."));

        customer.delete();
    }
}
