package com.example.demo.exception;

public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException(Long id) {
        super("Could not found pet id : " + id);
    }
}
