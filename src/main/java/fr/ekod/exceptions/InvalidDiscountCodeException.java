package fr.ekod.exceptions;

public class InvalidDiscountCodeException extends Exception {
    public InvalidDiscountCodeException(String message) {
        super(message);
    }
}
