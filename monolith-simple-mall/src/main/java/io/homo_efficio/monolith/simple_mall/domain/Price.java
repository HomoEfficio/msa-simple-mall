package io.homo_efficio.monolith.simple_mall.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@Embeddable
@Getter
@NoArgsConstructor
public class Price implements Comparable<BigDecimal> {

    private BigDecimal price;

    public Price(BigDecimal price) {
        this.price = price;
    }

    public Price plus(Price amount) {
        return new Price(price.add(amount.getPrice()));
    }

    public Price minus(Price amount) {
        return new Price(price.subtract(amount.getPrice()));
    }

    @Override
    public int compareTo(BigDecimal o) {
        return price.compareTo(o);
    }
}
