package com.telus.iofdoor.auth.api.model.exceptions;

public class UserNotApprovedException extends Exception {
 public UserNotApprovedException(String email){ super("User "+email+" Not Approved Yet");}
}
