package com.mikaioyamada.Gestao_Vagas.modules.candidate.controllers;

import com.mikaioyamada.Gestao_Vagas.exceptions.UserFoundException;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.CandidateEntity;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.CandidateRepository;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.DTO.ProfileCandidateResponseDTO;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import com.mikaioyamada.Gestao_Vagas.modules.company.entities.JobEntity;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name="Candidate", description = "Informações do candidato")
public class CandidateController {

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro do candidato", description = "Essa função é responsável por cadastrar o perfil do candidato")
    @ApiResponses({
            @ApiResponse(responseCode="200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário existente!")

    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity){
        try{
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato")
    @ApiResponses({
            @ApiResponse(responseCode="200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")

    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request){

        var idCandidate = request.getAttribute("candidate_id");
        try{
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis baseadas no filtro")
    @ApiResponses({
            @ApiResponse(responseCode="200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)) //recebrá um array porque a classe estará retornando uma lista
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter){
        return this.listAllJobsByFilterUseCase.execute(filter);
    }
}