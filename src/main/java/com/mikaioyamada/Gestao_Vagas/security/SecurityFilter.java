package com.mikaioyamada.Gestao_Vagas.security;

import com.mikaioyamada.Gestao_Vagas.providers.JWTprovider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
//filtro de permissão para chegar na controller


@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTprovider jwtprovider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //SecurityContextHolder.getContext().setAuthentication(null);//usado para manter nulo qualquer vestigio de autenticação
        String header = request.getHeader("Authorization"); //recupera o bearer token

        if(request.getRequestURI().startsWith("/company")){
            if(header != null){
                var token = this.jwtprovider.validateToken(header);
                if(token == null ){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                request.setAttribute("company_id",token.getSubject());

                var roles =  token.getClaim("roles").asList(Object.class);
                var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" +  role.toString().toUpperCase())).toList();
                //objetivo da linha 34,35,36 é manter um ciclo de validação, ou seja, durante o fluxo de requisição seja mantido a validação constante
                 //envia o id para o token JWT
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),null, grants);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request,response);
    }

}
