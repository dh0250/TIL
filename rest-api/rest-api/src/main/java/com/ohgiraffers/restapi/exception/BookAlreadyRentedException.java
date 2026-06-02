package com.ohgiraffers.restapi.exception;

public class BookAlreadyRentedException extends RuntimeException {
    public BookAlreadyRentedException(String message) {
        super(message);
    }
}
