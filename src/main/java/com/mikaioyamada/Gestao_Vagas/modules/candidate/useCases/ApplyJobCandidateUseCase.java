package com.mikaioyamada.Gestao_Vagas.modules.candidate.useCases;

import com.mikaioyamada.Gestao_Vagas.exceptions.JobNotFoundException;
import com.mikaioyamada.Gestao_Vagas.exceptions.UserNotFoundException;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.CandidateRepository;
import com.mikaioyamada.Gestao_Vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {
    //Precisamos receber na candidatura: Id do candidato e Id da vaga
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;
    public void execute(UUID idCandidate, UUID idJob){
        // Validar se o candidato existe
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        //Validar se a vaga existe.
        this.jobRepository.findById(idJob).orElseThrow(() ->{
            throw new JobNotFoundException();
        });


    }
}
