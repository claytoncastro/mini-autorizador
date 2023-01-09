package com.project.autorizador.domain.exception.handler;

import com.project.autorizador.domain.exception.ResourceAlreadyExistException;
import com.project.autorizador.domain.exception.details.ResourceAlreadyExistDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Object> handlerResourceAlreadyExistException(ResourceAlreadyExistException raeException) {
        ResourceAlreadyExistDetails resourceNotFound = ResourceAlreadyExistDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(UNPROCESSABLE_ENTITY.value())
                .title("Recurso já existe!!")
                .detail(raeException.getMessage())
                .developerMessage(raeException.getClass().getSimpleName())
                .build();
        return new ResponseEntity<>(resourceNotFound, UNPROCESSABLE_ENTITY);
    }

}
