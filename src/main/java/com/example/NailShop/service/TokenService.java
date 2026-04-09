package com.example.NailShop.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.NailShop.entity.Cliente;
import com.example.NailShop.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final String ISSUER = "NailShop API";

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        if(usuario.getAtivo() != true){
            throw new IllegalArgumentException("Usuário inátivo");
        }
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withClaim("perfil", usuario.getPerfil().name())
                    .withExpiresAt(dataExpiracao())
                    .sign(getAlgoritmo());
        } catch (JWTCreationException exception) {
            throw new IllegalStateException("Erro ao gerar token JWT.", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            return JWT.require(getAlgoritmo())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new IllegalArgumentException("Token JWT invalido ou expirado.", exception);
        }
    }

    private Algorithm getAlgoritmo() {
        return Algorithm.HMAC256(secret);
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now()
                .plusHours(5)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
