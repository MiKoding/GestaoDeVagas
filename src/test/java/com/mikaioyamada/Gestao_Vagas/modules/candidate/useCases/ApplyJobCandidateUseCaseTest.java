package com.mikaioyamada.Gestao_Vagas.modules.candidate.useCases;

import com.mikaioyamada.Gestao_Vagas.exceptions.UserNotFoundException;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.CandidateEntity;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.CandidateRepository;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.entity.ApplyJobEntity;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.repository.ApplyJobRepository;
import com.mikaioyamada.Gestao_Vagas.modules.company.entities.JobEntity;
import com.mikaioyamada.Gestao_Vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {
    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private JobRepository jobRepository; //mock é usado para informar que os repositorios são uma dependencia do inject mock
    @Mock
    private ApplyJobRepository applyJobRepository;
    @Test
    @DisplayName("Should not be able to aply job with candidate not found")
    public  void should_not_be_able_to_apply_job_with_candidateNotFound(){

        try{
            applyJobCandidateUseCase.execute(null,null);
        }catch(Exception e){
            assertThat(e).isInstanceOf(UserNotFoundException.class); //como eu entendi que isso funciona: o assertThat(e) devolve o erro obtido no try{} e o istanceOf é o erro esperado.
        }

    }
    @Test
    public void should_be_able_to_create_a_new_apply_job(){
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate)
                .jobId(idJob).build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
