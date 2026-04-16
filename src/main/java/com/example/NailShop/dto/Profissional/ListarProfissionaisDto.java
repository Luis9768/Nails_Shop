package com.example.NailShop.dto.Profissional;

import com.example.NailShop.entity.Profissionais;

public record ListarProfissionaisDto(
        String nome,
        String email,
        String contato
) {
    public ListarProfissionaisDto(Profissionais pr){
        this(
                pr.getNome(),
                pr.getEmail(),
                pr.getContato()
        );
    }
}
