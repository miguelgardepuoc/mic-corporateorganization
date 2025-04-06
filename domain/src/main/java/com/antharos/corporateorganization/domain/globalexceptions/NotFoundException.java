package com.antharos.corporateorganization.domain.globalexceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
