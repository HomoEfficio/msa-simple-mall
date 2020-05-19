package io.homo_efficio.monolith.simple_mall.dto;

import io.homo_efficio.monolith.simple_mall.domain.Customer;
import io.homo_efficio.monolith.simple_mall.domain.Product;
import io.homo_efficio.monolith.simple_mall.domain.ProductReview;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-20
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductReviewIn {

    @NotNull
    private Long productId;

    @NotNull
    private Long customerId;

    @NotEmpty
    private String comment;


    public ProductReview toEntityWith(Product product, Customer customer) {
        return new ProductReview(null, product, customer, comment);
    }

    public void updateEntity(ProductReview productReview) {
        productReview.changeComment(comment);
    }
}
