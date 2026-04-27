package com.ecommercebackoffice.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PaginationOrderDTO {
    private final List<ReadAllOrdersResponseDto> readAllOrdersResponseDtos;
    private final Integer page;
    private final Integer size;
    private final long totalOrder;
    private final Integer totalPage;

    public PaginationOrderDTO(List<ReadAllOrdersResponseDto> readAllOrdersResponseDtos, Integer page, Integer size, long totalOrder, Integer totalPage) {
        this.readAllOrdersResponseDtos = readAllOrdersResponseDtos;
        this.page = page;
        this.size = size;
        this.totalOrder = totalOrder;
        this.totalPage = totalPage;
    }
}
