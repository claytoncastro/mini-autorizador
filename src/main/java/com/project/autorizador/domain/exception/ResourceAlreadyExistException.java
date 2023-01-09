package com.project.autorizador.domain.exception;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String msg) {
        super(msg);
    }
}
