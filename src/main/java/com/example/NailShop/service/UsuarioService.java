package com.example.NailShop.service;

import com.example.NailShop.dto.Usuario.AtualizarUsuarioDto;
import com.example.NailShop.dto.Usuario.CadastroUsuarioDto;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public CadastroUsuarioDto Adicionar(CadastroUsuarioDto dto){
        if(repository.existsByEmail(dto.email())){
             throw new IllegalArgumentException("Email já cadastrado!");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setContato(dto.contato());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        repository.save(usuario);
        return new CadastroUsuarioDto(usuario);
    }
    @Transactional
    public AtualizarUsuarioDto Atualizar (Integer id, AtualizarUsuarioDto dto){
        Usuario usuarioBanco = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado!"));
        usuarioBanco.setNome(dto.nome());
        usuarioBanco.setContato(dto.contato());
        usuarioBanco.setEmail(dto.email());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuarioBanco.setSenha(passwordEncoder.encode(dto.senha()));
        }
        repository.save(usuarioBanco);
        return new AtualizarUsuarioDto(usuarioBanco);
    }
    public void InativarUsuario(Integer id){
        Usuario usuarioBanco = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado!"));
        usuarioBanco.setEmail("Usuario Inativo");
        usuarioBanco.setContato("Usuario Inativo");
        usuarioBanco.setAtivo(false);
        repository.save(usuarioBanco);
    }
}
