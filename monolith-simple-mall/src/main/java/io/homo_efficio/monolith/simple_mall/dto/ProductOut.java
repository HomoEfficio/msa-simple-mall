package io.homo_efficio.monolith.simple_mall.dto;

import io.homo_efficio.monolith.simple_mall.domain.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@RequiredArgsConstructor
@Getter
@ToString
public class ProductOut {

    private final Long id;
    private final String name;
    private final String description;
    private final SellerOut seller;
    private final String manufacturer;
    private final BigDecimal price;
    private final Long count;

    // Only for JacksonTester, DON'T use for production code
    public ProductOut() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.seller = null;
        this.manufacturer = null;
        this.price = null;
        this.count = null;
    }

    public static ProductOut from(Product product) {
        return new ProductOut(
                product.getId(),
                product.getName(),
                product.getDescription(),
                SellerOut.from(product.getSeller()),
                product.getManufacturer(),
                product.getPrice().getPrice(),
                product.getCount()
        );
    }
}
