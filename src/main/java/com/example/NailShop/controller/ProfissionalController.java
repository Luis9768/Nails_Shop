package com.example.NailShop.controller;

import com.example.NailShop.dto.Cliente.ListarClienteDto;
import com.example.NailShop.dto.Profissional.AtualizarProfissionalDto;
import com.example.NailShop.dto.Profissional.CriarProfissionalDto;
import com.example.NailShop.dto.Profissional.ListarProfissionaisDto;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissional")
public class ProfissionalController {
    @Autowired
    ProfissionalService service;

    @PostMapping
    public ResponseEntity<CriarProfissionalDto> adicionar(@Valid @RequestBody CriarProfissionalDto dto){
        var b = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(b);
    }
    @PutMapping
    public ResponseEntity<AtualizarProfissionalDto> atualizar(@RequestBody(required = false) AtualizarProfissionalDto dto, Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        var b = service.atualizar(usuario, dto);
        return ResponseEntity.ok(b);
    }
    @DeleteMapping
    public ResponseEntity<Void> inativar(Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        service.inativarProfissional(usuario);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ListarProfissionaisDto>> listar(Authentication authentication){
        Usuario usuario =(Usuario) authentication.getPrincipal();
        List<ListarProfissionaisDto> lista = service.listar(usuario);
        return ResponseEntity.ok(lista);
    }

}
