package io.homo_efficio.msa.simple_mall.biz.seller.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-06-29
 */
@Document
@Getter
public class Seller {

    @Id
    private String id;

    @Setter
    private String name;

    @Setter
    private String email;

    private String loginId;

    @Setter
    private String password;

    @Setter
    private String phone;

    public Seller(String id, String name, String email, String loginId, String password, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.phone = phone;
    }
}
