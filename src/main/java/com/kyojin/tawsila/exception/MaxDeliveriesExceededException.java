package com.kyojin.tawsila.exception;

public class MaxDeliveriesExceededException extends RuntimeException {
    public MaxDeliveriesExceededException(String message) {
        super(message);
    }
}
