package ru.otus.java.basic.http.server;

public class BadRequestException extends Throwable {
    public BadRequestException(String message) throws RuntimeException{
        super(message);
    }
}
