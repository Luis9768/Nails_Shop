package com.example.NailShop.dto.Usuario;

import com.example.NailShop.entity.Usuario;

public record UsuarioAutenticadoDto(
        Integer id,
        String nome,
        String email
) {
    public UsuarioAutenticadoDto(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
