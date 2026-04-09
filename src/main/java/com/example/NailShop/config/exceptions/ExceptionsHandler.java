package com.example.NailShop.config.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(
                erros.stream()
                        .map(DadosErroValidacao::new)
                        .toList()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MensagemDeErro> tratarErroData(MethodArgumentTypeMismatchException ex){
        MensagemDeErro erro = new MensagemDeErro();
        erro.setMensagem("O parametro "+ex.getName()+" recebeu um valor inválido "+ex.getValue()+".");
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensagemDeErro> tratarArgumetosIlegais(IllegalArgumentException ex){
        MensagemDeErro erro = new MensagemDeErro();
        erro.setMensagem(ex.getMessage());
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<MensagemDeErro> tratarConflito(ConflictException ex){
        MensagemDeErro erro = new MensagemDeErro();
        erro.setMensagem(ex.getMessage());
        erro.setStatus(HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensagemDeErro> tratarErroDeDuplicidade(DataIntegrityViolationException ex) {
        MensagemDeErro erro = new MensagemDeErro();
        erro.setMensagem("Conflito de dados no banco: O dado já existe ou é obrigatório.");
        erro.setStatus(HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemDeErro> tratarErro500(Exception ex){
        MensagemDeErro erro = new MensagemDeErro();
        erro.setMensagem("Erro interno do servidor.");
        erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
