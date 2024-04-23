package com.stocksapi.dto;

public class ExceptionResponse {

    private final int errorCode;
    private final String message;

    public ExceptionResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
