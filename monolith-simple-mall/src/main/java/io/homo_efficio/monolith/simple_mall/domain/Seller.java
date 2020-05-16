package io.homo_efficio.monolith.simple_mall.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-16
 */
@Entity
@Table(indexes = {
        @Index(name = "IDX_SELLER__EMAIL", columnList = "email"),
        @Index(name = "IDX_SELLER__LOGIN_ID", columnList = "login_id")
})
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Seller extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 120)
    @Setter
    private String name;

    @NotNull
    @Email
    @Column(length = 150, unique = true)
    @Setter
    private String email;

    @NotNull
    @Column(name = "login_id", length = 90, unique = true)
    private String loginId;

    @NotNull
    @Column(length = 30)
    @Setter
    private String password;

    @NotNull
    @Column(length = 20)
    @Setter
    private String phone;
}
