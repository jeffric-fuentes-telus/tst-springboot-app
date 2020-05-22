package com.telus.iofdoor.auth.api.model.dtos;

public class SaveUserMobileTokenRequest {
    String UserEmail;
    String Token;

    public SaveUserMobileTokenRequest() {
    }

    public SaveUserMobileTokenRequest(String userEmail, String token) {
        UserEmail = userEmail;
        Token = token;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
