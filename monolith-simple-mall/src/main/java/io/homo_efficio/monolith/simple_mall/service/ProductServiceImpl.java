package io.homo_efficio.monolith.simple_mall.service;

import io.homo_efficio.monolith.simple_mall._common.exception.EntityNotFoundException;
import io.homo_efficio.monolith.simple_mall.domain.Price;
import io.homo_efficio.monolith.simple_mall.domain.Product;
import io.homo_efficio.monolith.simple_mall.domain.Seller;
import io.homo_efficio.monolith.simple_mall.domain.repository.ProductRepository;
import io.homo_efficio.monolith.simple_mall.domain.repository.SellerRepository;
import io.homo_efficio.monolith.simple_mall.dto.ProductIn;
import io.homo_efficio.monolith.simple_mall.dto.ProductOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Override
    public ProductOut create(ProductIn productIn) {
        String sellerLoginId = productIn.getSellerLoginId();
        Seller seller = sellerRepository.findByLoginId(sellerLoginId)
                .orElseThrow(() -> new EntityNotFoundException(Seller.class,
                        String.format("LoginId 가 [%s] 인 판매자는 존재하지 않습니다.", sellerLoginId)));
        Product product = productIn.toEntityWith(seller);

        return ProductOut.from(productRepository.save(product));
    }

    @Override
    public ProductOut update(Long id, ProductIn productIn) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
        productIn.updateEntity(product);
        return ProductOut.from(product);
    }

    @Override
    public Long delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
        productRepository.delete(product);
        return id;
    }

    @Override
    public ProductOut findById(Long id) {
        return ProductOut.from(productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id)));
    }

    @Override
    public List<ProductOut> findAllByNameContaining(String name) {
        return productRepository.findAllByNameContaining(name).stream()
                .map(ProductOut::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOut> findAllByDescriptionContaining(String description) {
        return productRepository.findAllByDescriptionContaining(description).stream()
                .map(ProductOut::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOut> findAllByManufacturerContaining(String manufacturer) {
        return productRepository.findAllByManufacturerContaining(manufacturer).stream()
                .map(ProductOut::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOut> findAllByPriceGreaterThan(Price price) {
        return productRepository.findAllByPriceGreaterThan(price).stream()
                .map(ProductOut::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOut> findAllByPriceLessThan(Price price) {
        return productRepository.findAllByPriceLessThan(price).stream()
                .map(ProductOut::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOut> findAllBySeller_Id(Long id) {
        return productRepository.findAllBySeller_Id(id).stream()
                .map(ProductOut::from)
                .collect(Collectors.toList());
    }
}
