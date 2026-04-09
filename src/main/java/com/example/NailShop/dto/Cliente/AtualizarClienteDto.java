package com.example.NailShop.dto.Cliente;

import com.example.NailShop.entity.Cliente;

public record AtualizarClienteDto(
        String nome,
        String email,
        String contato,
        String senha
) {
    public AtualizarClienteDto(Cliente cliente){
        this(
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getContato(),
                null
        );
    }
}
