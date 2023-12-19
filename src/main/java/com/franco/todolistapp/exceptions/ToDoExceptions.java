package com.franco.todolistapp.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


public class ToDoExceptions extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;
    private static final Logger logger = LoggerFactory.getLogger(ToDoExceptions.class);

    public ToDoExceptions(String message,HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.logger.error("Error occurred: {}", this, message, getStackTrace());
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
