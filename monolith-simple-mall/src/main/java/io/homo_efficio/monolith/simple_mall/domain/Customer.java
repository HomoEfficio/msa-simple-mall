package io.homo_efficio.monolith.simple_mall.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-05-19
 */
@Entity
@Table(indexes = {
        @Index(name = "IDX_CUSTOMER__EMAIL", columnList = "email"),
        @Index(name = "IDX_CUSTOMER__LOGIN_ID", columnList = "login_id")
})
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {
    
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
    @Column(length = 20)
    @Setter
    private String phone;

    @NotNull
    @Setter
    @Embedded
    private Address address;

    @NotNull
    @Column(name = "login_id", length = 90, unique = true)
    private String loginId;

    @NotNull
    @Column(length = 70)
    @Setter
    private String password;
}
