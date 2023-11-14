package com.mikaioyamada.Gestao_Vagas.modules.candidate.useCases;

import com.mikaioyamada.Gestao_Vagas.modules.company.entities.JobEntity;
import com.mikaioyamada.Gestao_Vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterUseCase {
    @Autowired
    private JobRepository jobRepository;
    public List<JobEntity> execute(String filter){

        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);

    }
}
