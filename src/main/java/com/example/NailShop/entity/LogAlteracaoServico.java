package com.example.NailShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "logAlteracaoServico")
@Getter
@Setter
public class LogAlteracaoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logAlteracaoServicoId", nullable = false)
    private Integer logAlteracaoServicoId;

    @ManyToOne
    @JoinColumn(name = "servicoId", nullable = false)
    private Servico servico;

    @Size(max = 200)
    @NotBlank
    private String descricao;

    @NotNull
    private LocalDateTime dataHoraAlteracao;

}
