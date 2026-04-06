package com.example.NailShop.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUsuarioDto(
        @NotBlank @Email String email,
        @NotBlank String senha
) {
}
