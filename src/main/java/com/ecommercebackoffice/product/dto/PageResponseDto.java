package com.ecommercebackoffice.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

// <ProductListResponseDto> -> <T>로 제네릭 처리
@Getter
@RequiredArgsConstructor
public class PageResponseDto<T> {
    // 목록 조회시 페이징 정보와 데이터 반환
    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean first;
    private final boolean last;

    public PageResponseDto(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
    }
}
