package com.aadi.yummy.exception;

public class InvalidFilePathException extends  RuntimeException{
    public InvalidFilePathException(String message) {
        super(message);
    }
    public InvalidFilePathException() {
        super("Invalid file Path");
    }

}
