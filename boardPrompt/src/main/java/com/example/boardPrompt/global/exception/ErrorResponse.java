package com.example.boardPrompt.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final String message;
    private final List<FieldError> errors;

    public ErrorResponse(String message) {
        this.message = message;
        this.errors = null;
    }

    public ErrorResponse(String message, List<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
