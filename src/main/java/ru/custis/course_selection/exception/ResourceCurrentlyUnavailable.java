package ru.custis.course_selection.exception;

public class ResourceCurrentlyUnavailable extends RuntimeException {

    public ResourceCurrentlyUnavailable(String message) {
        super(message);
    }
}