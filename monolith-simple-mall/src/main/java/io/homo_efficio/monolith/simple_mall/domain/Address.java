package io.homo_efficio.monolith.simple_mall.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-19
 */
@Embeddable
@Getter
@RequiredArgsConstructor
public class Address {

    @Column(length = 5)
    private final String zipNo;

    @Column(length = 90)
    private final String roadAddrPart1;

    @Column(length = 60)
    private final String roadAddrPart2;

    @Column(length = 60)
    private final String addrDetail;

    public Address() {
        this.zipNo = null;
        this.roadAddrPart1 = null;
        this.roadAddrPart2 = null;
        this.addrDetail = null;
    }
}
