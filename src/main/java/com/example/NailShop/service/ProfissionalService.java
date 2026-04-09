package com.example.NailShop.service;

import com.example.NailShop.config.exceptions.ConflictException;
import com.example.NailShop.dto.Profissional.CriarProfissionalDto;
import com.example.NailShop.entity.Perfil;
import com.example.NailShop.entity.Profissionais;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.repository.ProfissionalRepository;
import com.example.NailShop.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public CriarProfissionalDto adicionar (CriarProfissionalDto dto){
        if(profissionalRepository.existsByEmail(dto.email())){
            throw new ConflictException("Email já cadastrado.");
        }
        Usuario usuario = new Usuario();
        Profissionais profissionais = new Profissionais();

        String senha = passwordEncoder.encode(dto.senha());

        usuario.setEmail(dto.email());
        usuario.setSenha(senha);
        usuario.setPerfil(Perfil.ADMIN);
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);

        profissionais.setNome(dto.nome());
        profissionais.setEmail(dto.email());
        profissionais.setContato(dto.contato());
        profissionais.setSenha(senha);
        profissionais.setPerfil(Perfil.ADMIN);
        profissionalRepository.save(profissionais);
        return new CriarProfissionalDto(profissionais);
    }


}
