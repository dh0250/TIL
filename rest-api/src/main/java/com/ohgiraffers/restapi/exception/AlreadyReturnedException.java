package com.ohgiraffers.restapi.exception;

public class AlreadyReturnedException extends RuntimeException {
    public AlreadyReturnedException(String message) {
        super(message);
    }
}
