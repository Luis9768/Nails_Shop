package com.example.NailShop.controller;

import com.example.NailShop.dto.Cliente.AtualizarClienteDto;
import com.example.NailShop.dto.Cliente.CadastroClienteDto;
import com.example.NailShop.dto.Cliente.ListarClienteDto;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<CadastroClienteDto> adicionar(@Valid @RequestBody CadastroClienteDto dto){
        CadastroClienteDto usuario = service.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
    @PutMapping()
    public ResponseEntity<AtualizarClienteDto> atualizar(@Valid @RequestBody(required = false) AtualizarClienteDto dto, Authentication authentication){
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        AtualizarClienteDto usuarioAtualizar = service.atualizar(usuarioLogado, dto);
        return ResponseEntity.ok(usuarioAtualizar);
    }
    @DeleteMapping()
    public ResponseEntity<Void> inativarUsuario(Authentication authentication){
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        service.inativarUsuario(usuarioLogado);
        return ResponseEntity.noContent().build();
    }
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ListarClienteDto>> listar(Authentication authentication){
        Usuario usuario =(Usuario) authentication.getPrincipal();
        List<ListarClienteDto> lista = service.listar(usuario);
        return ResponseEntity.ok(lista);
    }

}
