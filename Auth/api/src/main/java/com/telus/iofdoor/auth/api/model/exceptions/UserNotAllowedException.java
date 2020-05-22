package com.telus.iofdoor.auth.api.model.exceptions;

public class UserNotAllowedException extends Exception {
    public UserNotAllowedException(String email){super("The User "+email+" Is Not Allowed.");}
}
