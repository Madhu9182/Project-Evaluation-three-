package io.capgemini.com.exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Wrong Password");
    }
}
