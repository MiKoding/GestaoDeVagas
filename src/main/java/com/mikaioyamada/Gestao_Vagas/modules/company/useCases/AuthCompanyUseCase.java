package com.mikaioyamada.Gestao_Vagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mikaioyamada.Gestao_Vagas.modules.company.dto.AuthCompanyDTO;
import com.mikaioyamada.Gestao_Vagas.modules.company.dto.AuthCompanyResponseDTO;
import com.mikaioyamada.Gestao_Vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(()->{
            throw new UsernameNotFoundException("Username / password incorrect");
        });
//verifica a senha encriptografada
        var passwordMatcher = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword() );
//se não for igual retorna erro
        if(!passwordMatcher){
            throw new AuthenticationException();
        }
//se for igual gera token

        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm); //gera o token

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder().access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authCompanyResponseDTO;
    }

}
