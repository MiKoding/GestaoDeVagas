package com.mikaioyamada.Gestao_Vagas.modules.company.repositories;

import com.mikaioyamada.Gestao_Vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID>{
    //contains - like
    //select * from job where description like %filter%
    List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);
}
