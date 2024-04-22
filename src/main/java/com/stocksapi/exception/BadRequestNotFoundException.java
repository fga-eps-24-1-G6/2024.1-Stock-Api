package com.stocksapi.exception;

public class BadRequestNotFoundException  extends RuntimeException {

    private final String message;

    public BadRequestNotFoundException(String mensagem) {
        super(mensagem);
        this.message = mensagem;
    }

    public String getMensagem() {
        return message;
    }

}
