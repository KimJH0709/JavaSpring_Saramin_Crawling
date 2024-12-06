package com.kjh.wsd.saramIn_crawling.auth.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}