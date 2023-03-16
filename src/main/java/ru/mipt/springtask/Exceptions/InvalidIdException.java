package ru.mipt.springtask.Exceptions;

public class InvalidIdException extends Exception {
    public InvalidIdException(String errorMessage) {
        super(errorMessage);
    }
}
