package com.mikaioyamada.Gestao_Vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
//dto significa data trasnfer object
@Data
@AllArgsConstructor
public class AuthCompanyDTO {
    private String password;
    private String username;
}
