package com.example.NailShop.service;

import com.example.NailShop.dto.Usuario.CadastroUsuarioDto;
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
    private PasswordEncoder encoder;

    @Transactional
    public CadastroUsuarioDto Adicionar(CadastroUsuarioDto dto){
        if(repository.existsByEmail(dto.email()){
            throw new IllegalAccessException("Email já cadastrado!");
        }
    }
}
