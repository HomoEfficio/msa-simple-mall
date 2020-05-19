package io.homo_efficio.monolith.simple_mall.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-19
 */
@Entity
@Table(indexes = {
        @Index(name = "IDX_PRODUCT_REVIEW__PRODUCT_ID", columnList = "product_id"),
        @Index(name = "IDX_PRODUCT_REVIEW__CUSTOMER_ID", columnList = "customer_id")
})
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer customer;

    @NotNull
    @Lob
    private String comment;


    public void changeComment(String comment) {
        this.comment = comment;
    }
}
