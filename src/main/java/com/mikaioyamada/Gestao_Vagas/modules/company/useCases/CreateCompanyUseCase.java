package com.mikaioyamada.Gestao_Vagas.modules.company.useCases;

import com.mikaioyamada.Gestao_Vagas.exceptions.UserFoundException;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.CandidateEntity;
import com.mikaioyamada.Gestao_Vagas.modules.company.entities.CompanyEntity;
import com.mikaioyamada.Gestao_Vagas.modules.company.repositories.CompanyRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateCompanyUseCase {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public CompanyEntity execute(CompanyEntity companyEntity){

        this.companyRepository
                .findByUsernameOrEmail(companyEntity.getUsername(),companyEntity.getEmail())
                .ifPresent((user)->{
                    throw new UserFoundException();// mensagem de erro caso o user ou email for existente
                });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);
        return this.companyRepository.save(companyEntity);
    }
}
