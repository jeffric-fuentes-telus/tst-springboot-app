package com.telus.iofdoor.auth.api.services;

import com.telus.iofdoor.auth.api.model.dtos.CreateUserRequest;
import com.telus.iofdoor.auth.api.model.dtos.DeleteUserMobileTokenRequest;
import com.telus.iofdoor.auth.api.model.dtos.GetUserRequest;
import com.telus.iofdoor.auth.api.model.dtos.SaveUserMobileTokenRequest;
import com.telus.iofdoor.auth.api.model.entities.User;
import com.telus.iofdoor.auth.api.model.exceptions.*;
import com.telus.iofdoor.auth.api.repositories.implementations.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.exceptions.JedisConnectionException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User GetUserByEmail(GetUserRequest request) throws UserNotFoundException, ServerSideException, UserNotApprovedException, UserNotAllowedException {
        User user=new User();
            try{
             user = userRepository.GetUserInfoByEmail(request.getEmail());
            if((user.getUserId()==null || user.getUserId()==""))
                throw  new UserNotFoundException(request.getEmail());

            if( user.getStatus().contains("pending") ){
                throw  new UserNotApprovedException(user.getEmail());
            }
           if( user.getStatus().contains("denied") ){
                    throw  new UserNotAllowedException(user.getEmail());
              }
            }
            catch (UserNotFoundException ex){
                System.err.println(ex.getMessage());
                throw new UserNotFoundException(request.getEmail());
            }
            catch (NullPointerException ex){
                System.err.println(ex.getMessage());
                throw new UserNotFoundException((request.getEmail()));
            }
            catch (JedisConnectionException ex){
                System.err.println(ex.getMessage());
                throw new ServerSideException();
            }
        return  user;
    }

    public void CreateUser(CreateUserRequest request) throws UserAlreadyExistException{
        try{
            if((request.getUserEmail()!=null||request.getUserEmail()!="") && (request.getUserId()!=null || request.getUserId()!="")){
                if(userRepository.GetUserInfoByEmail(request.getUserEmail()).getUserId()==null) {
                    User userToCreate = new User();
                    userToCreate.setFullName(request.getUserFullName());
                    userToCreate.setEmail(request.getUserEmail());
                    userToCreate.setUserId(request.getUserId());
                    userToCreate.setStatus("pending"); //ByDefault
                    userToCreate.setCreatedAt(LocalDateTime.now());
                    userRepository.CreateUser(userToCreate);
                }else{
                    throw new UserAlreadyExistException(request.getUserEmail());
                }
            }

        }catch(UserAlreadyExistException ex){
            throw new UserAlreadyExistException(request.getUserEmail());
        }
    }

    public boolean SaveUserToken(SaveUserMobileTokenRequest request){
        try{
            if ((request.getUserEmail()!="" || request.getUserEmail()!=null) && (request.getToken()!="" || request.getToken()!=null)){

                List<String> tokens = userRepository.GetTokensByUserEmail(request.getUserEmail());
                if(tokens.contains(request.getToken())){
                    return true;
                }else{
                    return userRepository.SaveUserMobileToken(request);
                }
            }else return false;
        }catch (JedisConnectionException ex){
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public List<String> getMobileTokensByEmail(String email){
        try{
            if(email!=null || !email.isEmpty())
            return userRepository.GetTokensByUserEmail(email);
            else return new ArrayList<String>();
        }catch (JedisConnectionException ex){
            return  new ArrayList<String>();
        }
    }

    public boolean deleteUserMobileToken(DeleteUserMobileTokenRequest request){
        try{
            return userRepository.DeleteUserMobileToken(request);
        }catch (JedisConnectionException ex){
            return false;
        }
    }


    
}
