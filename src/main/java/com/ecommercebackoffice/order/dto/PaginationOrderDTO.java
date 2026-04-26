package com.ecommercebackoffice.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PaginationOrderDTO {
    private final List<ReadAllOrdersResponseDto> readAllOrdersResponseDtos;
    private final int page;
    private final int size;
    private final long totalOrder;
    private final int totalPage;

    public PaginationOrderDTO(List<ReadAllOrdersResponseDto> readAllOrdersResponseDtos, int page, int size, long totalOrder, int totalPage) {
        this.readAllOrdersResponseDtos = readAllOrdersResponseDtos;
        this.page = page;
        this.size = size;
        this.totalOrder = totalOrder;
        this.totalPage = totalPage;
    }
}
