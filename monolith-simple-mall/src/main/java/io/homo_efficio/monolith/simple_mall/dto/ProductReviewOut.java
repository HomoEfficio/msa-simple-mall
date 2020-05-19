package io.homo_efficio.monolith.simple_mall.dto;

import io.homo_efficio.monolith.simple_mall.domain.ProductReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-20
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductReviewOut {

    private final Long productId;
    private final String productName;

    private final Long customerId;
    private final String customerName;

    private final String comment;

    // Only for JacksonTester, DON'T use for production code
    public ProductReviewOut() {
        this.productId = null;
        this.productName = null;
        this.customerId = null;
        this.customerName = null;
        this.comment = null;
    }

    public static ProductReviewOut from(ProductReview productReview) {
        return new ProductReviewOut(
                productReview.getProduct().getId(),
                productReview.getProduct().getName(),
                productReview.getCustomer().getId(),
                productReview.getCustomer().getName(),
                productReview.getComment()
        );
    }
}
