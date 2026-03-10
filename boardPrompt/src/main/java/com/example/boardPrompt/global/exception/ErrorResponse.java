package com.example.boardPrompt.global.exception;

import java.util.List;

public class ErrorResponse {

    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final List<FieldError> errors;

    public ErrorResponse(int status, String error, String message, String path, List<FieldError> errors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public record FieldError(String field, String message) {
    }
}

