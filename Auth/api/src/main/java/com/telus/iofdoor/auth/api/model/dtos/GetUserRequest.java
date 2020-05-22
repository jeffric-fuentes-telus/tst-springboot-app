package com.telus.iofdoor.auth.api.model.dtos;

public class GetUserRequest {
    public String email;
    public GetUserRequest(){}

    public GetUserRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
