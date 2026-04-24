package com.ecommercebackoffice.customer.service;

import com.ecommercebackoffice.common.exception.NotFoundException;
import com.ecommercebackoffice.customer.dto.*;
import com.ecommercebackoffice.customer.entity.Customer;
import com.ecommercebackoffice.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<SearchCustomerResponseDto> findAll() {
        return customerRepository.findAllByDeletedAtIsNull().stream()
                .map(customer -> new SearchCustomerResponseDto(
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhoneNumber(),
                        customer.getStatus(),
                        customer.getCreatedAt()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public SearchCustomerResponseDto findOne(Long customerId) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId).orElseThrow(
                () -> new NotFoundException("고객 id가 존재하지 않습니다.")
        );
        return new SearchCustomerResponseDto(
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getStatus(),
                customer.getCreatedAt()
        );
    }

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

    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new NotFoundException("고객 id가 존재하지 않습니다."));

        customer.delete();
    }
}
