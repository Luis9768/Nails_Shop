package com.example.NailShop.service;

import com.example.NailShop.config.exceptions.ConflictException;
import com.example.NailShop.dto.Cliente.AtualizarClienteDto;
import com.example.NailShop.dto.Cliente.CadastroClienteDto;
import com.example.NailShop.dto.Cliente.ListarClienteDto;
import com.example.NailShop.entity.Perfil;
import com.example.NailShop.entity.Cliente;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.repository.ClienteRepository;
import com.example.NailShop.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public CadastroClienteDto adicionar(CadastroClienteDto dto){
        if(clienteRepository.existsByEmail(dto.email())){
             throw new ConflictException("Email já cadastrado!");
        }
        String senha = passwordEncoder.encode(dto.senha());

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.email());
        usuario.setSenha(senha);
        usuario.setAtivo(true);
        usuario.setPerfil(Perfil.CLIENTE);
        
        usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setNome(dto.nome());
        cliente.setContato(dto.contato());
        cliente.setEmail(dto.email());
        cliente.setSenha(senha);
        cliente.setPerfil(Perfil.CLIENTE);
        
        clienteRepository.save(cliente);
        return new CadastroClienteDto(cliente);
    }
    
    @Transactional
    public AtualizarClienteDto atualizar (Usuario usuario, AtualizarClienteDto dto){
        Cliente clienteBanco = clienteRepository.findByEmail(usuario.getEmail()).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));
        Usuario usuarioBanco = clienteBanco.getUsuario();

        if(dto.email() != null && !dto.email().isBlank()) {
            usuarioBanco.setEmail(dto.email());
            clienteBanco.setEmail(dto.email());
        }
        if(dto.nome() != null && !dto.nome().isBlank()){
        clienteBanco.setNome(dto.nome());
        }
        if(dto.contato() !=null && !dto.contato().isBlank()) {
            clienteBanco.setContato(dto.contato());
        }
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
    
    public void inativarUsuario(Usuario usuarioLogado){

        Cliente clienteBanco = clienteRepository.findByEmail(usuarioLogado.getEmail()).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));
        Usuario usuarioBanco = clienteBanco.getUsuario(); // Correção: Reaproveita a busca

        usuarioBanco.setAtivo(false);
        clienteBanco.setAtivo(false);

        clienteRepository.save(clienteBanco);
        usuarioRepository.save(usuarioBanco);
    }

    public List<ListarClienteDto> listar(Usuario usuarioLogado){
        var ehAdmin = usuarioLogado.getPerfil() == Perfil.ADMIN;
        if (!ehAdmin) {
            throw new IllegalArgumentException("Você não tem permissão para ver os usuários!");
        }
        return clienteRepository.findAll().stream().map(ListarClienteDto::new).toList();
    }

}
