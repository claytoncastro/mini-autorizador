package com.project.autorizador.domain.exception;

public class ResourceUnprocessableException extends RuntimeException{
    public ResourceUnprocessableException(String msg) {
        super(msg);
    }
}
