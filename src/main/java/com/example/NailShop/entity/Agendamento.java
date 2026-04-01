package com.example.NailShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "agendamento")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agendamentoId", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "horaInicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull
    @Column(name = "horaFim", nullable = false)
    private LocalTime horaFim;

    @NotNull
    @Column(name = "dataAgendamento", nullable = false)
    private LocalDate dataAgendamento;

    @Size(max = 300)
    @Column(name = "observacoes", length = 300)
    private String observacoes;

    @Size(max = 20)
    @NotNull
    @Column(name = "statusAgendamento", nullable = false, length = 20)
    private String statusAgendamento;


}