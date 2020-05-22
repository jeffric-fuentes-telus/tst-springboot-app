package com.telus.iofdoor.auth.api.controllers;

import com.telus.iofdoor.auth.api.model.dtos.CreateUserRequest;
import com.telus.iofdoor.auth.api.model.dtos.DeleteUserMobileTokenRequest;
import com.telus.iofdoor.auth.api.model.dtos.GetUserRequest;
import com.telus.iofdoor.auth.api.model.dtos.SaveUserMobileTokenRequest;
import com.telus.iofdoor.auth.api.model.entities.User;
import com.telus.iofdoor.auth.api.model.exceptions.*;
import com.telus.iofdoor.auth.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/Users")
public class UsersController {

    @Autowired
    UserService userService;

    @PostMapping("/GetUserInfo")
    public ResponseEntity getUserInfo(@RequestBody GetUserRequest request ){
        if(request==null || request.getEmail()==null){
            return  ResponseEntity.badRequest().body("Unexpected Request Body");
        }
        try {
            User user = userService.GetUserByEmail(request);
            if(user.getUserId()!=null){
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");
            }
        }catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (UserNotAllowedException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        catch (UserNotApprovedException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        catch (ServerSideException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/CreateUser")
    public ResponseEntity createUser(@RequestBody CreateUserRequest request ){
        if((request==null || request.getUserId()==null) && (request==null || request.getUserFullName()==null)){
            return  ResponseEntity.badRequest().body("Unexpected Request Body");
        }
        try {
            userService.CreateUser(request);
            return  ResponseEntity.status(HttpStatus.OK).body("User Created Succefully ");
        }catch (UserAlreadyExistException ex)
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
        }
    }

    @PostMapping("/SaveNotificationToken")
    public ResponseEntity saveUserToken(@RequestBody SaveUserMobileTokenRequest request){
      boolean isSaved =  userService.SaveUserToken(request);
      return isSaved?ResponseEntity.status(HttpStatus.OK).body("Mobile Token Saved"):ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Request Not Valid");
    }
    @PostMapping("/RemoveNotificationToken")
    public ResponseEntity removeUserToken(@RequestBody DeleteUserMobileTokenRequest request){
        boolean isRemoved =  userService.deleteUserMobileToken(request);
        return isRemoved?ResponseEntity.status(HttpStatus.OK).body("Mobile Token Removed"):ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Request Not Valid");
    }

}
