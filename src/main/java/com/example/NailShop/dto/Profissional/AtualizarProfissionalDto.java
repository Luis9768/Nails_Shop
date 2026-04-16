package com.example.NailShop.dto.Profissional;

import com.example.NailShop.entity.Profissionais;

public record AtualizarProfissionalDto(
        String nome,
        String email,
        String contato,
        String senha
) {
    public AtualizarProfissionalDto(Profissionais profissionais){
        this(
                profissionais.getNome(),
                profissionais.getEmail(),
                profissionais.getContato(),
                null
        );
    }
}
