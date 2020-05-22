package com.telus.iofdoor.auth.api.model.exceptions;

import java.security.PublicKey;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String Email){
        super("The User "+Email+" Was Not Found");
    }
}
