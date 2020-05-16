package io.homo_efficio.monolith.simple_mall.domain;

import io.homo_efficio.monolith.simple_mall._common.exception.NoStockRemainedException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@Entity
@Table(indexes = {
        @Index(name = "IDX_PRODUCT__NAME", columnList = "name")
})
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    private String manufacturer;

    @NotNull
    @Embedded
    private Price price;

    @NotNull
    @Min(value = 0)
    private Long count;

    private Product(Product.Builder builder) {
        name = builder.name;
        description = builder.description;
        manufacturer = builder.manufacturer;
        price = builder.price;
        count = builder.count;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void changePrice(Price price) {
        this.price = price;
    }

    public void sold(Long amount) {
        this.count -= amount;
        if (this.count < 0) {
            throw new NoStockRemainedException(this.count, amount);
        }
    }

    public static class Builder {

        private final String name;
        private final Price price;
        private final Long count;

        private String description;
        private String manufacturer;

        public Builder(String name, Price price, Long count) {
            this.name = name;
            this.price = price;
            this.count = count;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
