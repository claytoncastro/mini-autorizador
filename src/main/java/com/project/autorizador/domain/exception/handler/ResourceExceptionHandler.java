package com.project.autorizador.domain.exception.handler;

import com.project.autorizador.domain.exception.ResourceAlreadyExistException;
import com.project.autorizador.domain.exception.ResourceNotFoundException;
import com.project.autorizador.domain.exception.ResourceUnprocessableException;
import com.project.autorizador.domain.exception.details.ResourceAlreadyExistDetails;
import com.project.autorizador.domain.exception.details.ResourceNotFoundDetails;
import com.project.autorizador.domain.exception.details.ResourceUnprocessableDetails;
import com.project.autorizador.domain.exception.details.ValidationErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

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

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ValidationErrorDetails veDetails = ValidationErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(BAD_REQUEST.value())
                .title("Erro de validação de campo")
                .detail("Erro de validação de campo")
                .developerMessage(manvException.getClass().getSimpleName())
                .field(fields)
                .fieldMessage(fieldMessages)
                .build();
        return new ResponseEntity<>(veDetails, BAD_REQUEST);
    }

}
