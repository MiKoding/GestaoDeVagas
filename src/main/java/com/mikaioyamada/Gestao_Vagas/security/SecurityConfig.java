package com.mikaioyamada.Gestao_Vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration //vai dizer pro spring que esta é umma classe de configuração.
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private SecurityFilter securityFilter;
    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;

    private static final String[] SWAGGER_LIST = {
            "/swagger-ui/**",
            "v3/api-docs/**",
            ".swagger-resources/**"
    };
    @Bean //esse metodo é usado para sobreescrever a camada original existente.
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // essa linha esta sendo usada para desabilitar o crsf, que é uma vulnerabilidade de segurança, para que seja configurado de maneira personalizada
        http.csrf(crsf -> crsf.disable())
                .authorizeRequests(auth -> {auth
                        .requestMatchers("/candidate/").permitAll()
                        .requestMatchers("/company/").permitAll()
                        .requestMatchers("company/auth").permitAll()
                        .requestMatchers("/candidate/auth").permitAll()
                        .requestMatchers(SWAGGER_LIST).permitAll();
                auth.anyRequest().authenticated();})
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ // é utilizado para encryptar a senha
        return new BCryptPasswordEncoder();
    }
}
