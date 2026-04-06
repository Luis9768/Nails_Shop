package com.example.NailShop.dto.Usuario;

import com.example.NailShop.entity.Usuario;

public record AtualizarUsuarioDto(
        String nome,
        String email,
        String contato,
        String senha
) {
    public AtualizarUsuarioDto(Usuario usuario){
        this(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getContato(),
                null
        );
    }
}
