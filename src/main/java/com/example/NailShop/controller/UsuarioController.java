package com.example.NailShop.controller;

import com.example.NailShop.dto.Usuario.AtualizarUsuarioDto;
import com.example.NailShop.dto.Usuario.CadastroUsuarioDto;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<CadastroUsuarioDto> Adicionar(@Valid @RequestBody CadastroUsuarioDto dto){
        CadastroUsuarioDto usuario = service.Adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AtualizarUsuarioDto> Atualizar(@Valid @RequestBody AtualizarUsuarioDto dto, Authentication authentication){
        var usuarioLogado = (Usuario) authentication.getPrincipal();
        var usuarioAtualizar = service.Atualizar(usuarioLogado.getId(), dto);

        return ResponseEntity.ok(usuarioAtualizar);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> InativarUsuario(Authentication authentication){
        var usuarioLogado = (Usuario) authentication.getPrincipal();
        service.InativarUsuario(usuarioLogado.getId());
        return ResponseEntity.noContent().build();
    }
}
