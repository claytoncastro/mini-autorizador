package com.project.autorizador.domain.exception.handler;

import com.project.autorizador.domain.exception.ResourceAlreadyExistException;
import com.project.autorizador.domain.exception.ResourceNotFoundException;
import com.project.autorizador.domain.exception.ResourceUnprocessableException;
import com.project.autorizador.domain.exception.details.ResourceAlreadyExistDetails;
import com.project.autorizador.domain.exception.details.ResourceNotFoundDetails;
import com.project.autorizador.domain.exception.details.ResourceUnprocessableDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Object> handlerResourceAlreadyExistException(ResourceAlreadyExistException raeException) {
        ResourceAlreadyExistDetails resourceAlreadyExist = ResourceAlreadyExistDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(UNPROCESSABLE_ENTITY.value())
                .title("Recurso já existe!!")
                .detail(raeException.getMessage())
                .developerMessage(raeException.getClass().getSimpleName())
                .build();
        return new ResponseEntity<>(resourceAlreadyExist, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handlerResourceNotFoundException(ResourceNotFoundException rnfException) {
        ResourceNotFoundDetails resourceNotFound = ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(NOT_FOUND.value())
                .title("Recurso não existe!!")
                .detail(rnfException.getMessage())
                .developerMessage(rnfException.getClass().getSimpleName())
                .build();
        return new ResponseEntity<>(resourceNotFound, NOT_FOUND);
    }

    @ExceptionHandler(ResourceUnprocessableException.class)
    public ResponseEntity<Object> handlerResourceUnprocessableException(ResourceUnprocessableException ruException) {
        ResourceUnprocessableDetails resourceUnrocessable = ResourceUnprocessableDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(UNPROCESSABLE_ENTITY.value())
                .title("Recurso não pode ser processado!!")
                .detail(ruException.getMessage())
                .developerMessage(ruException.getClass().getSimpleName())
                .build();
        return new ResponseEntity<>(resourceUnrocessable, UNPROCESSABLE_ENTITY);
    }

}
