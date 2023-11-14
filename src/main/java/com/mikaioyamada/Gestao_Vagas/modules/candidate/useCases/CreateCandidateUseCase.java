package com.mikaioyamada.Gestao_Vagas.modules.candidate.useCases;

import com.mikaioyamada.Gestao_Vagas.exceptions.UserFoundException;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.CandidateEntity;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public CandidateEntity execute (CandidateEntity candidateEntity){
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) ->{
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
}
