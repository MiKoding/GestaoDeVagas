package com.mikaioyamada.Gestao_Vagas.modules.candidate.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileCandidateResponseDTO {
    @Schema(example = "Desenvolvedora Java")
    private String description;

    @Schema(example = "mariasouza")
    private String username;

    @Schema(example = "mariadesouza@gmail.com")
    private String email;

    @Schema(example = "Maria de Souza")
    private String name;
    private UUID id;

}
