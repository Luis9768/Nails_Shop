package com.example.NailShop.dto.Cliente;

import com.example.NailShop.entity.Perfil;
import com.example.NailShop.entity.Cliente;

public record CadastroClienteDto(
        String nome,
        String email,
        String contato,
        String senha,
        Perfil perfil
) {
    public CadastroClienteDto(Cliente cliente){
        this(
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getContato(),
                null,
                cliente.getPerfil()
        );
    }
}
