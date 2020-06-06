package io.homo_efficio.msa.simple_mall.formation.edge_server.auth;

import lombok.Data;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-06-06
 */
@Data
public class Customer {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String zipNo;
    private final String roadAddrPart1;
    private final String roadAddrPart2;
    private final String addrDetail;
    private final String loginId;
    private final String password;
}
