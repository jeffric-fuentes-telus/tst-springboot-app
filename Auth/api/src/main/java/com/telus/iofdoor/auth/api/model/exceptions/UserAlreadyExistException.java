package com.telus.iofdoor.auth.api.model.exceptions;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String email){
        super("The user "+email+" already exist");
    }
}
