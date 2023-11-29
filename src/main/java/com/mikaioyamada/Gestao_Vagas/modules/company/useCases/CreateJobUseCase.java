package com.mikaioyamada.Gestao_Vagas.modules.company.useCases;

import com.mikaioyamada.Gestao_Vagas.exceptions.CompanyNotFoundException;
import com.mikaioyamada.Gestao_Vagas.modules.company.entities.JobEntity;
import com.mikaioyamada.Gestao_Vagas.modules.company.repositories.CompanyRepository;
import com.mikaioyamada.Gestao_Vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;
    public JobEntity execute(JobEntity jobEntity){
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        return this.jobRepository.save(jobEntity);
    }

}
