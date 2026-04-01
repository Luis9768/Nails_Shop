package com.example.NailShop.dto.Usuario;

import com.example.NailShop.entity.Usuario;

public record CadastroUsuarioDto(
        String nome,
        String email,
        String contato,
        String senha
) {
    public CadastroUsuarioDto(Usuario usuario){
        this(
                usuario.getNome(),
                usuario.getContato(),
                usuario.getEmail(),
                null
        );
    }
}
