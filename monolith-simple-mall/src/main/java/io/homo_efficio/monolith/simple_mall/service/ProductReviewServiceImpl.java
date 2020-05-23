package io.homo_efficio.monolith.simple_mall.service;

import io.homo_efficio.monolith.simple_mall._common.exception.EntityNotFoundException;
import io.homo_efficio.monolith.simple_mall.domain.Customer;
import io.homo_efficio.monolith.simple_mall.domain.Product;
import io.homo_efficio.monolith.simple_mall.domain.repository.CustomerRepository;
import io.homo_efficio.monolith.simple_mall.domain.repository.ProductRepository;
import io.homo_efficio.monolith.simple_mall.domain.repository.ProductReviewRepository;
import io.homo_efficio.monolith.simple_mall.dto.ProductReviewIn;
import io.homo_efficio.monolith.simple_mall.dto.ProductReviewOut;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-20
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository prRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ProductReviewOut create(ProductReviewIn productReviewIn) {
        Long productId = productReviewIn.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, productId));
        Long customerId = productReviewIn.getCustomerId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(Customer.class, customerId));
        return ProductReviewOut.from(prRepository.save(productReviewIn.toEntityWith(product, customer)));
    }

    @Override
    public ProductReviewOut update(Long id, ProductReviewIn productReviewIn) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductReviewOut findById(Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductReviewOut> findAllByProductId(Long productId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductReviewOut> findAllByCustomerId(Long customerId, Pageable pageable) {
        return prRepository.findAllByCustomer_Id(customerId, pageable)
                .map(ProductReviewOut::from);
    }
}
