package com.ecommercebackoffice.product.service;

import com.ecommercebackoffice.admin.entity.Admin;
import com.ecommercebackoffice.product.dto.*;
import com.ecommercebackoffice.product.entity.Product;
import com.ecommercebackoffice.product.entity.ProductCategory;
import com.ecommercebackoffice.product.entity.ProductStatus;
import com.ecommercebackoffice.product.repository.ProductRepository;
import com.ecommercebackoffice.review.dto.LatestReview;
import com.ecommercebackoffice.review.dto.ReviewStatistics;
import com.ecommercebackoffice.review.entity.Review;
import com.ecommercebackoffice.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    // 입력 받은 정보로 새로운 상품 등록
    @Transactional
    public Long createProduct(CreateProductRequestDto request, Admin adminId) {
        Product product = new Product(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getStock(),
                request.getStatus(),
                adminId
        );

        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    // 검색, 필터, 페이징, 정렬 조건에 따라 상품 목록 조회
    @Transactional(readOnly = true)
    public PageResponseDto<ProductListResponseDto> getProducts(
            String keyword,
            ProductCategory category,
            ProductStatus status,
            int page,
            int size,
            String sortBy,
            String sortOrder
    ) {
        int pageNumber = Math.max(page - 1, 0);
        int pageSize = size > 0 ? size : 10;

        // 정렬 조건 생성
        Sort sort = createSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<Product> productPage = productRepository.findByConditions(
                keyword, category, status, pageable
        );

        List<ProductListResponseDto> responseList = new ArrayList<>();
        for (Product product : productPage.getContent()) {
            responseList.add(new ProductListResponseDto(product));
        }

        // 변환된 DTO 리스트를 다시 Page 객체로 감싼다
        Page<ProductListResponseDto> responsePage = new PageImpl<>(
                responseList,
                pageable,
                productPage.getTotalElements()
        );
        return new PageResponseDto<>(responsePage);
    }

    // 정렬 메서드 분리
    private Sort createSort(String sortBy, String sortOrder) {
        String field;
        if (sortBy.equals("price")) {
            field = "price";
        } else if (sortBy.equals("stock")) {
            field = "stock";
        } else {
            field = "createdAt";
        }

        Sort.Direction direction;
        if (sortOrder.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        return Sort.by(direction, field);
    }

    // 상품 id로 상세 정보 조회
    @Transactional
    public ProductDetailResponseDto getProduct(Long id) {
        Product product = findProductById(id);

        Admin admin = product.getAdminId();

        // 최신 리뷰 3개 가져오기
        List<Review> latestReviews = reviewRepository.findLatestReviewsByProductId(id);

        // 리뷰 없으면 null 반환
        if (latestReviews == null) {
            return new ProductDetailResponseDto(
                    product,
                    admin.getName(),
                    admin.getEmail(),
                    null,
                    null);
        }

        // 최신 리뷰 있을 경우 dto 변환
        List<LatestReview> latestReviewsDtos = latestReviews
                .stream()
                .map(review -> new LatestReview(
                        review.getOrder().getCustomer().getName(),
                        review.getRating(),
                        review.getContent(),
                        review.getCreatedAt()))
                .toList();

        // 상품 리뷰 통계 가져오기
        ReviewStatistics reviewStatistics = reviewRepository.getReviewStatistics(id);

        return new ProductDetailResponseDto(
                product,
                admin.getName(),
                admin.getEmail(),
                reviewStatistics,
                latestReviewsDtos);
    }

    // 상품명, 카테고리, 가격 수정
    @Transactional
    public void updateProduct(Long id, UpdateProductRequestDto request) {

        // ID로 상품 조회
        Product product = findProductById(id);

        // 엔티티 수정 메서드 호출
        product.updateInfo(
                request.getName(),
                request.getCategory(),
                request.getPrice()
        );
    }

    // 재고 변경, 자동 상태 전환
    @Transactional
    public void changeStock(Long id, ChangeStockRequestDto request) {

        Product product = findProductById(id);

        // 재고 변경
        product.changeStock(request.getAmount());
    }

    // 관리자가 수동으로 상품 상태 변경
    @Transactional
    public void changeStatus(Long id, ChangeStatusRequestDto request) {

        Product product = findProductById(id);

        // 상태 변경
        product.changeStatus(request.getStatus());
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {

        Product product = findProductById(id);

        // DB에서 상품 삭제
        productRepository.delete(product);
    }

    // 상품 조회 로직
    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("상품을 찾을 수 없습니다. ID: " + id));
    }
}
