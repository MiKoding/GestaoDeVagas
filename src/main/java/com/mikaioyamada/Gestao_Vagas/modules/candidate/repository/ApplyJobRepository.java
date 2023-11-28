package com.mikaioyamada.Gestao_Vagas.modules.candidate.repository;

import com.mikaioyamada.Gestao_Vagas.modules.candidate.entity.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {

}
