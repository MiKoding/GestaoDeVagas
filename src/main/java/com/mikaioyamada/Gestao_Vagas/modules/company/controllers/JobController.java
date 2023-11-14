package com.mikaioyamada.Gestao_Vagas.modules.company.controllers;

import com.mikaioyamada.Gestao_Vagas.modules.company.dto.CreateJobDTO;
import com.mikaioyamada.Gestao_Vagas.modules.company.entities.JobEntity;
import com.mikaioyamada.Gestao_Vagas.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name="Vagas", description = "Informações das vagas")
    @Operation(summary = "Cadastro de Vagas", description = "Essa função é responsável por cadastrar as vagas da empresa")
    @ApiResponses({
            @ApiResponse(responseCode="200", content = {
                    @Content(
                            schema = @Schema(implementation = JobEntity.class)                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request){
        var companyId = request.getAttribute("company_id");
        //jobEntity.setCompanyId(UUID.fromString(companyId.toString()));

        var jobEntity = JobEntity.builder().benefits(createJobDTO.getBenefits())
                .companyId(UUID.fromString(companyId.toString()))
                .description(createJobDTO.getDescription())
                .level(createJobDTO.getLevel())
                .build(); //refatoração usada para deixar as

        return this.createJobUseCase.execute(jobEntity);

    }
}