package io.capgemini.com.exception;

public class UserNameNotFoundException extends Exception{
    public UserNameNotFoundException() {
        super("User Name not Found");
    }
}
