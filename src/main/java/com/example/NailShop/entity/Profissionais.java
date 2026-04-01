package com.example.NailShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "profissionais")
public class Profissionais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profissionalId", nullable = false)
    private Integer id;

    @Size(max = 80)
    @NotNull
    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Size(max = 80)
    @NotNull
    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Size(max = 80)
    @NotNull
    @Column(name = "contato", nullable = false, length = 80)
    private String contato;

    @Size(max = 255)
    @NotNull
    @Column(name = "senha", nullable = false)
    private String senha;

    @ColumnDefault("1")
    @Column(name = "ativo")
    private Boolean ativo;


}