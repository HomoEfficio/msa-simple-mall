package io.homo_efficio.msa.simple_mall.formation.edge_server.auth;

import lombok.Data;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-06-06
 */
@Data
public class Seller {
    private Long id;
    private String name;
    private String email;
    private String loginId;
    private String password;
    private String phone;
}
