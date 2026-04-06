package com.example.NailShop.controller;

import com.example.NailShop.dto.Usuario.UsuarioAutenticadoDto;
import com.example.NailShop.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<UsuarioAutenticadoDto> me(Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok(new UsuarioAutenticadoDto(usuario));
    }
}
