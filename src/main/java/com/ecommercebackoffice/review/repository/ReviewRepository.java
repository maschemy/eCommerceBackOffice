package com.ecommercebackoffice.review.repository;

import com.ecommercebackoffice.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
