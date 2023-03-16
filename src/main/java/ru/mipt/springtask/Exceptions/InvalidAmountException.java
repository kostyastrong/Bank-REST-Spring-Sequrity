package ru.mipt.springtask.Exceptions;

public class InvalidAmountException extends Throwable {
    public InvalidAmountException(String errorMessage) {
        super(errorMessage);
    }
}
