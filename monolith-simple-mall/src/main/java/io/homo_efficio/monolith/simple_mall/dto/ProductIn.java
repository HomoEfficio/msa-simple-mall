package io.homo_efficio.monolith.simple_mall.dto;

import io.homo_efficio.monolith.simple_mall.domain.Price;
import io.homo_efficio.monolith.simple_mall.domain.Product;
import io.homo_efficio.monolith.simple_mall.domain.Seller;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductIn {

    @NotEmpty
    @Size(min = 1, max = 150, message = "이름은 최소 1, 최대 50자 입니다.")
    private String name;

    @Size(max = 255, message = "상품 설명은 최대 80자 입니다.")
    private String description;

    @NotEmpty
    private String sellerLoginId;

    @Size(max = 90, message = "제조자는 최대 30자 입니다.")
    private String manufacturer;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Long count;

    public Product toEntityWith(Seller seller) {
        return new Product.Builder(name, seller, new Price(price), count)
                .description(description)
                .manufacturer(manufacturer)
                .build();
    }

    public void updateEntity(Product product) {
        product.changeDescription(description);
        product.changeName(name);
        product.changeManufacturer(manufacturer);
        product.changePrice(new Price(price));
    }
}
