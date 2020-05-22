package com.telus.iofdoor.auth.api.model.exceptions;

public class ServerSideException extends Exception {
   public ServerSideException(){
        super("Server Side Error, Contact Admins");
    }
}
