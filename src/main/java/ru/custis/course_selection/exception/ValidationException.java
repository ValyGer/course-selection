package ru.custis.course_selection.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}