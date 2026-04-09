package com.example.NailShop.dto.Profissional;

import com.example.NailShop.entity.Perfil;
import com.example.NailShop.entity.Profissionais;

public record CriarProfissionalDto(
        String nome,
        String email,
        String contato,
        String senha
) {
    public CriarProfissionalDto(Profissionais profissionais) {
        this(
                profissionais.getNome(),
                profissionais.getEmail(),
                profissionais.getContato(),
                null
        );
    }
}
