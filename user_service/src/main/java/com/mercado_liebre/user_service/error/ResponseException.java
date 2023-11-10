package com.mercado_liebre.user_service.error;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ResponseException extends RuntimeException{
    private HttpStatus httpStatus;
    private String error;

    public ResponseException(String message, String error, HttpStatus httpStatus) {
        super(message);
        this.error = error;
        this.httpStatus = httpStatus;
    }

}
