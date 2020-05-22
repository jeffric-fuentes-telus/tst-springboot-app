package com.telus.iofdoor.auth.api.repositories.implementations;

import com.telus.iofdoor.auth.api.model.dtos.DeleteUserMobileTokenRequest;
import com.telus.iofdoor.auth.api.model.dtos.SaveUserMobileTokenRequest;
import com.telus.iofdoor.auth.api.model.entities.User;
import com.telus.iofdoor.auth.api.repositories.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class UserRepository implements IUserRepository {

    @Autowired
    private Jedis jedis;

    @Override
    public User GetUserInfoByEmail(String Email) throws JedisConnectionException {
       User user = new User();
       try{
           Set<String> keys= jedis.keys("USERS."+Email.toUpperCase()+".*");
           if(!keys.isEmpty()){
              Map<String,String> userData = jedis.hgetAll(keys.iterator().next());
              user.setEmail(userData.get("email"));
               user.setUserId(userData.get("userId"));
               user.setFullName(userData.get("fullName"));
               user.setStatus(userData.get("status"));
               user.setCreatedAt(LocalDateTime.parse(userData.get("createdAt")));
           }
       }catch (JedisConnectionException ex){
           System.err.println(ex.getMessage());
           throw new JedisConnectionException(ex.getMessage());
       }
       return user;
    }

    @Override
    public User CreateUser(User user) throws JedisConnectionException{
        String key ="USERS."+user.getEmail().toUpperCase()+"."+user.getUserId();
        try{

                jedis.hset(key, "userId", user.getUserId());
                jedis.hset(key, "email", user.getEmail());
                jedis.hset(key, "fullName", user.getFullName());
                jedis.hset(key, "status",user.getStatus());
                jedis.hset(key, "createdAt",user.getCreatedAt().toString());
                return user;
        }catch (JedisConnectionException ex){
            System.err.println(ex.getMessage());
            throw new JedisConnectionException(ex.getMessage());
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
            return new User();
        }
    }

    @Override
    public boolean SaveUserMobileToken(SaveUserMobileTokenRequest request) throws JedisConnectionException{
        if((request.getUserEmail()!=null || request.getUserEmail()!="") && (request.getToken()!=null || request.getUserEmail()!="")){
           long valid = jedis.lpush("MOBILETOKENS."+request.getUserEmail().toUpperCase(),request.getToken() );
            if(valid>0)
            return true;
            else return  false;
        }else return false;
    }

    @Override
    public List<String> GetTokensByUserEmail(String UserEmail) throws  JedisConnectionException {
        return  jedis.lrange("MOBILETOKENS."+UserEmail.toUpperCase(),0 ,-1);
    }

    @Override
    public boolean DeleteUserMobileToken(DeleteUserMobileTokenRequest request) throws JedisConnectionException{
        return jedis.lrem("MOBILETOKENS"+request.getUserEmail().toUpperCase(),1,request.getToken())>0?true:false;
    }
}
