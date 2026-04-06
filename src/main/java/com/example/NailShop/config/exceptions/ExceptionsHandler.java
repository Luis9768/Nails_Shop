package com.example.NailShop.config.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(
                erros.stream()
                        .map(DadosErroValidacao::new)
                        .toList()
        );
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity tratarErroData(MethodArgumentTypeMismatchException ex){
        String mensagem = ("O parametro "+ex.getName()+" recebeu um valor inválido "+ex.getValue()+".");
        return ResponseEntity.badRequest().body(mensagem);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity tratarArgumetosIlegais(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity tratarErro403() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Você não tem permissão para realizar esta ação.");
    }
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity tratarErro500(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("O servidor explodiu!");
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> tratarErroDeDuplicidade(DataIntegrityViolationException ex) {
        // Aqui você devolve o erro bonitinho, sem expor o banco de dados!
        return ResponseEntity.badRequest().body("Erro: O dado informado (como CPF ou Email) já está em uso por outro usuário no sistema!");
    }
}
