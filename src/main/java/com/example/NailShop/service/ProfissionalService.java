package com.example.NailShop.service;

import com.example.NailShop.config.exceptions.ConflictException;
import com.example.NailShop.dto.Profissional.AtualizarProfissionalDto;
import com.example.NailShop.dto.Profissional.CriarProfissionalDto;
import com.example.NailShop.dto.Profissional.ListarProfissionaisDto;
import com.example.NailShop.entity.Perfil;
import com.example.NailShop.entity.Profissionais;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.repository.ProfissionalRepository;
import com.example.NailShop.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public CriarProfissionalDto adicionar(CriarProfissionalDto dto) {
        if (profissionalRepository.existsByEmail(dto.email())) {
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
        profissionais.setUsuario(usuario);
        profissionais.setContato(dto.contato());
        profissionais.setSenha(senha);
        profissionais.setPerfil(Perfil.ADMIN);
        profissionalRepository.save(profissionais);
        return new CriarProfissionalDto(profissionais);
    }

    @Transactional
    public AtualizarProfissionalDto atualizar(Usuario usuarioLogado, AtualizarProfissionalDto dto) {
        Profissionais profissionalBanco = profissionalRepository.findByEmail(usuarioLogado.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado."));

        Usuario usuarioBanco = profissionalBanco.getUsuario();

        if (dto.email() != null && !dto.email().isBlank()) {
            profissionalBanco.setEmail(dto.email());
            usuarioBanco.setEmail(dto.email());
        }
        if (dto.nome() != null && !dto.nome().isBlank()) {
            profissionalBanco.setNome(dto.nome());
        }
        if (dto.contato() != null && !dto.contato().isBlank()) {
            profissionalBanco.setContato(dto.contato());
        }
        if (dto.senha() != null && !dto.senha().isBlank()) {
            String novaSenhaCriptografada = passwordEncoder.encode(dto.senha());
            usuarioBanco.setSenha(novaSenhaCriptografada);
            profissionalBanco.setSenha(novaSenhaCriptografada);
        }

        usuarioRepository.save(usuarioBanco);
        profissionalRepository.save(profissionalBanco);
        return new AtualizarProfissionalDto(profissionalBanco);
    }
    @Transactional
    public void inativarProfissional(Usuario usuarioLogado){
        Profissionais profissionaisBanco = profissionalRepository.findByEmail(usuarioLogado.getEmail()).orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado."));
        Usuario usuario = profissionaisBanco.getUsuario();
        
        profissionaisBanco.setAtivo(false);
        usuario.setAtivo(false);
        
        usuarioRepository.save(usuario);
        profissionalRepository.save(profissionaisBanco);
    }
    public List<ListarProfissionaisDto> listar(Usuario usuarioLogado){
        var ehAdmin = usuarioLogado.getPerfil() == Perfil.ADMIN;
        if (!ehAdmin) {
            throw new IllegalArgumentException("Você não tem permissão para ver os usuários!");
        }
        return profissionalRepository.findAll().stream().map(ListarProfissionaisDto::new).toList();
    }


}
