package com.example.NailShop.controller;

import com.example.NailShop.dto.Cliente.AtualizarClienteDto;
import com.example.NailShop.dto.Cliente.CadastroClienteDto;
import com.example.NailShop.entity.Cliente;
import com.example.NailShop.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<CadastroClienteDto> Adicionar(@Valid @RequestBody CadastroClienteDto dto){
        CadastroClienteDto usuario = service.Adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AtualizarClienteDto> Atualizar(@Valid @RequestBody AtualizarClienteDto dto, Authentication authentication){
        var usuarioLogado = (Cliente) authentication.getPrincipal();
        var usuarioAtualizar = service.Atualizar(usuarioLogado.getId(), dto);

        return ResponseEntity.ok(usuarioAtualizar);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> InativarUsuario(Authentication authentication){
        var usuarioLogado = (Cliente) authentication.getPrincipal();
        service.InativarUsuario(usuarioLogado.getId());
        return ResponseEntity.noContent().build();
    }
}
