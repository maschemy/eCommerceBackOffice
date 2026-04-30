package com.ecommercebackoffice.customer.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponseDto<T> {
        // 목록 조회시 페이징 정보와 데이터 반환
        private final List<T> content;
        private final int page;
        private final int size;
        private final long totalElements;
        private final int numberOfElements;
        private final boolean first;
        private final boolean last;

        public PageResponseDto(Page<T> page) {
            this.content = page.getContent();
            this.page = page.getNumber() + 1;
            this.size = page.getSize();
            this.totalElements = page.getTotalElements();
            this.numberOfElements = page.getNumberOfElements();
            this.first = page.isFirst();
            this.last = page.isLast();
        }
}
