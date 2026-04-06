package com.example.NailShop.controller;

import com.example.NailShop.dto.Usuario.LoginUsuarioDto;
import com.example.NailShop.dto.Usuario.TokenJwtDto;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenJwtDto> login(@RequestBody @Valid LoginUsuarioDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());

        try {
            var authentication = authenticationManager.authenticate(authenticationToken);
            var usuario = (Usuario) authentication.getPrincipal();
            var token = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(new TokenJwtDto(token));
        } catch (BadCredentialsException ex) {
            throw new IllegalArgumentException("Email ou senha invalidos.");
        }
    }
}
