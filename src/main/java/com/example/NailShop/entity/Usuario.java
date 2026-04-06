package com.example.NailShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuarioId", nullable = false)
    private Integer id;

    @Size(max = 60)
    @NotNull
    @Column(name = "nome", nullable = false, length = 60)
    private String nome;

    @Size(max = 80)
    @NotNull
    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "senha", nullable = false)
    private String senha;

    @Size(max = 20)
    @NotNull
    @Column(name = "contato", nullable = false, length = 20)
    private String contato;

    @ColumnDefault("1")
    @Column(name = "ativo")
    private Boolean ativo = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(ativo);
    }
}
