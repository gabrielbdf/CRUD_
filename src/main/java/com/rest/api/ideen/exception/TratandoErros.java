package com.rest.api.ideen.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratandoErros {


        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity tratarErro404() {
            return ResponseEntity.notFound().build();
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        //declarando a exception na assinatura do metodo para ela me devolver o erro com json, se fizer como o acima, apenas me retorna o numero do erro
        public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
            var erros = ex.getFieldErrors();//getFiedErro, me devolve a lista dos objs que deram erro
            return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
        }

        //dto...dados da dto add apos erros. iniciando strean
    private record DadosErroValidacao(String campo, String mesagem){
            public  DadosErroValidacao(FieldError erro){
                this(erro.getField(), erro.getDefaultMessage());
            }              //campo          mesagen

    }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

}
