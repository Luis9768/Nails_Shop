package com.example.NailShop.dto.Usuario;

import com.example.NailShop.entity.Perfil;
import com.example.NailShop.entity.Cliente;

public record UsuarioAutenticadoDto(
        Integer id,
        String nome,
        String email,
        Perfil perfil
) {
    public UsuarioAutenticadoDto(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getPerfil());
    }
}
