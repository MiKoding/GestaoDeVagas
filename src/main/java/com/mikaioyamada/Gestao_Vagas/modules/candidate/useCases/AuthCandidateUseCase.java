package com.mikaioyamada.Gestao_Vagas.modules.candidate.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.CandidateRepository;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.DTO.AuthCandidateRequestDTO;
import com.mikaioyamada.Gestao_Vagas.modules.candidate.DTO.AuthCandidateResponseDTO;
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
public class AuthCandidateUseCase {
    @Value("${security.token.secret.candidate}")
    private String secretKey;
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(()->{
                    throw new UsernameNotFoundException("Username / password incorrect");
                }); //verifica se o username é existente

        var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());//verifica se a senha é compatível com a senha criptografada

        if(!passwordMatches){
            throw new AuthenticationException();
        } // caso não devolve erro

        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        Algorithm algorithm = Algorithm.HMAC256(secretKey); //

        var token = JWT.create().withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("CANDIDATE")) //passa a role candidate para o token JWT
                .withExpiresAt(expiresIn) //captura o horario atual e adiciona 2horas a mais
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder().access_token(token)
                .expires_in(expiresIn.toEpochMilli()).build();
        return authCandidateResponse;

    }
}
