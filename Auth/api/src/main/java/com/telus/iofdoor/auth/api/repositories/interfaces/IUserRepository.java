package com.telus.iofdoor.auth.api.repositories.interfaces;

import com.telus.iofdoor.auth.api.model.dtos.DeleteUserMobileTokenRequest;
import com.telus.iofdoor.auth.api.model.dtos.SaveUserMobileTokenRequest;
import com.telus.iofdoor.auth.api.model.entities.User;

import java.util.List;

public interface  IUserRepository {
    public User GetUserInfoByEmail(String Email);
    public User CreateUser(User user);
    public boolean SaveUserMobileToken(SaveUserMobileTokenRequest request);
    public List<String> GetTokensByUserEmail(String UserEmail);
    public boolean DeleteUserMobileToken(DeleteUserMobileTokenRequest request);
}
