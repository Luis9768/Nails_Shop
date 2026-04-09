package com.example.NailShop.dto.Cliente;

import com.example.NailShop.entity.Cliente;

public record ListarClienteDto(
        String nome,
        String contato,
        String email
) {
    public  ListarClienteDto(Cliente cliente){
        this(
                cliente.getNome(),
                cliente.getContato(),
                cliente.getEmail()
        );
    }
}
