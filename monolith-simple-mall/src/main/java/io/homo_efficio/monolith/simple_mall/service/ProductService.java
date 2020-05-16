package io.homo_efficio.monolith.simple_mall.service;

import io.homo_efficio.monolith.simple_mall.domain.Price;
import io.homo_efficio.monolith.simple_mall.dto.ProductIn;
import io.homo_efficio.monolith.simple_mall.dto.ProductOut;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
public interface ProductService {

    ProductOut create(ProductIn productIn);

    ProductOut update(Long id, ProductIn productIn);

    Long delete(Long id);

    ProductOut findById(Long id);

    List<ProductOut> findAllByNameContaining(String name);
    List<ProductOut> findAllByDescriptionContaining(String description);
    List<ProductOut> findAllByManufacturerContaining(String manufacturer);
    List<ProductOut> findAllByPriceGreaterThan(Price price);
    List<ProductOut> findAllByPriceLessThan(Price price);
    List<ProductOut> findAllBySeller_Id(Long id);
}
