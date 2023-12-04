package org.example.exceptions;

public class CustomException extends Exception {

    private String message;

    public CustomException(String message) {
        super(message);
        this.message=message;
    }


    @Override
    public String toString() {
        return "custom Exception : " + message;
    }


}
