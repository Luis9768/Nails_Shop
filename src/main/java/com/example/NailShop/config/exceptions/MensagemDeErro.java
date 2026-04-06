package com.example.NailShop.config.exceptions;

import lombok.Data;

@Data
public class MensagemDeErro {
    private String mensagem;
    private Integer status;
}