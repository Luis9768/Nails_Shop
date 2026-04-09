package com.example.NailShop.dto.Usuario;

import com.example.NailShop.entity.Perfil;
import com.example.NailShop.entity.Cliente;
import com.example.NailShop.entity.Usuario;

public record UsuarioAutenticadoDto(
        Integer id,
        String email,
        Perfil perfil
) {
    public UsuarioAutenticadoDto(Usuario usuario) {
        this(usuario.getId(), usuario.getEmail(), usuario.getPerfil());
    }
}
