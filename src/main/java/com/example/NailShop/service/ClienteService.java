package com.example.NailShop.service;

import com.example.NailShop.dto.Cliente.AtualizarClienteDto;
import com.example.NailShop.dto.Cliente.CadastroClienteDto;
import com.example.NailShop.entity.Perfil;
import com.example.NailShop.entity.Cliente;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.repository.ClienteRepository;
import com.example.NailShop.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public CadastroClienteDto Adicionar(CadastroClienteDto dto){
        if(clienteRepository.existsByEmail(dto.email())){
             throw new IllegalArgumentException("Email já cadastrado!");
        }
        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.email());
        usuario.setSenha(senhaCriptografada);
        usuario.setAtivo(true);
        usuario.setPerfil(Perfil.CLIENTE);
        
        usuarioRepository.save(usuario); // Correção: Salva o usuário primeiro

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setNome(dto.nome());
        cliente.setContato(dto.contato());
        cliente.setEmail(dto.email());
        cliente.setSenha(senhaCriptografada);
        cliente.setPerfil(Perfil.CLIENTE);
        
        clienteRepository.save(cliente);
        return new CadastroClienteDto(cliente);
    }
    
    @Transactional
    public AtualizarClienteDto Atualizar (Cliente cliente, AtualizarClienteDto dto){
        Cliente clienteBanco = clienteRepository.findByEmail(cliente.getUsuario().getEmail()).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));
        Usuario usuarioBanco = clienteBanco.getUsuario();

        usuarioBanco.setEmail(dto.email());
        
        clienteBanco.setNome(dto.nome());
        clienteBanco.setContato(dto.contato());
        clienteBanco.setEmail(dto.email());
        
        // Correção: Atualiza a senha APENAS se o usuário enviou uma nova
        if (dto.senha() != null && !dto.senha().isBlank()) {
            String novaSenhaCriptografada = passwordEncoder.encode(dto.senha());
            usuarioBanco.setSenha(novaSenhaCriptografada);
            clienteBanco.setSenha(novaSenhaCriptografada);
        }
        
        usuarioRepository.save(usuarioBanco);
        clienteRepository.save(clienteBanco);
        return new AtualizarClienteDto(clienteBanco);
    }
    
    public void InativarUsuario(Usuario usuarioLogado){
        Cliente clienteBanco = clienteRepository.findByEmail(usuarioLogado.getEmail()).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));
        Usuario usuarioBanco = clienteBanco.getUsuario(); // Correção: Reaproveita a busca

        usuarioBanco.setAtivo(false);
        clienteBanco.setAtivo(false);

        clienteRepository.save(clienteBanco);
        usuarioRepository.save(usuarioBanco);
    }
}
