package io.homo_efficio.monolith.simple_mall.dto;

import io.homo_efficio.monolith.simple_mall.domain.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@RequiredArgsConstructor
@Getter
public class ProductOut {

    private final Long id;
    private final String name;
    private final String description;
    private final SellerOut seller;
    private final String manufacturer;
    private final BigDecimal price;
    private final Long count;

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
