package com.example.NailShop.config.security;

import com.example.NailShop.repository.ProfissionalRepository;
import com.example.NailShop.repository.UsuarioRepository;
import com.example.NailShop.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Pega o token do cabeçalho (Authorization: Bearer xxxxx)
        var tokenJWT = recuperarToken(request);
        System.out.println("Token recebido: " + tokenJWT); // Log para depuração

        if (tokenJWT != null) {
            // 2. Valida o token e pega o e-mail (subject)
            var email = tokenService.getSubject(tokenJWT);
            System.out.println("🚨 [FILTRO] Token válido! Email encontrado: " + email);

            // 3. Busca o usuário no banco
            var usuario = repository.findByEmail(email).orElse(null);

            if (usuario != null) {
                System.out.println("🚨 [FILTRO] Usuário carregado do banco com sucesso! Email: " + usuario.getEmail());
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }// Cria a autenticação do Spring e força o login
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }
        return null;
    }
}
