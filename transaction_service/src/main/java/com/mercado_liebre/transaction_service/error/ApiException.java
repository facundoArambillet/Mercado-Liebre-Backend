package com.mercado_liebre.transaction_service.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException {
    private String message;
    private String cause;
    private HttpStatus httpStatus;

    public ApiException(String message, String cause, HttpStatus httpStatus) {
        this.message = message;
        this.cause = cause;
        this.httpStatus = httpStatus;
    }
    public ApiException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
