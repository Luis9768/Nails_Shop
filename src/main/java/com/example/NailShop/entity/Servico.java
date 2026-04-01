package com.example.NailShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "servicos")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servicoId", nullable = false)
    private Integer id;

    @Size(max = 80)
    @NotNull
    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @NotNull
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "tempoDuracao", nullable = false)
    private Integer tempoDuracao;

    @ColumnDefault("1")
    @Column(name = "ativo")
    private Boolean ativo;


}