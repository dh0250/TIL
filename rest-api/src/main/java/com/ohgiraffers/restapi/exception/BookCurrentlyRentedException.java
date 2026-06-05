package com.ohgiraffers.restapi.exception;

public class BookCurrentlyRentedException extends RuntimeException {
    public BookCurrentlyRentedException(String message) {
        super(message);
    }
}
