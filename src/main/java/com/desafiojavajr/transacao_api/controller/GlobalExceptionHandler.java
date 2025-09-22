package com.desafiojavajr.transacao_api.controller;
import com.desafiojavajr.transacao_api.infrastructure.exceptions.UnprocessableEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

// anotação que torna a classe um tratador de excecoes global
@ControllerAdvice
public class GlobalExceptionHandler {

    // trata a excecao especifica UnprocessableEntity
    @ExceptionHandler(UnprocessableEntity.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // define o status http de resposta
    public ResponseEntity<String> handleUnprocessableEntity(UnprocessableEntity e) {
        // retorna uma resposta com o status 422 e a mensagem de erro
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("erro: " + e.getMessage());
    }

    // trata qualquer outra excecao generica que nao foi tratada antes
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // define o status http 500
    public ResponseEntity<String> handleUnprocessableEntity(Exception e) {
        // retorna uma resposta com o status 500 e a mensagem de erro
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("erro: " + e.getMessage());
    }
}