package com.ecommercebackoffice.customer.controller;

import com.ecommercebackoffice.customer.dto.*;
import com.ecommercebackoffice.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<SearchCustomerResponseDto>> getAllCustomer(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<SearchCustomerResponseDto> getOneCustomer(@PathVariable Long customerId){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findOne(customerId));
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<UpdateCustomerResponseDto> updateCustomerInfo(
            @Valid
            @RequestBody UpdateCustomerRequestDto request,
            @PathVariable Long customerId
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateInfo(request, customerId));
    }

    @PatchMapping("/{customerId}/status")
    public ResponseEntity<UpdateCustomerStatusResponseDto> updateCustomerStatus(
            @Valid @RequestBody UpdateCustomerStatusRequestDto request,
            @PathVariable Long customerId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateStatus(request,customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerId(@PathVariable Long customerId){
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
