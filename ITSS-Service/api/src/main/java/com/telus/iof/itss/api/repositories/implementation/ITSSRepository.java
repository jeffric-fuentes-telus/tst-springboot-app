package com.telus.iof.itss.api.repositories.implementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telus.iof.itss.api.model.dto.HistoryITSSRegister;
import com.telus.iof.itss.api.model.dto.catalogs.Catalog1Value;
import com.telus.iof.itss.api.model.entities.ITSSRegistry;
import com.telus.iof.itss.api.repositories.interfaces.IITSSRepository;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.exceptions.JedisConnectionException;
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public class ITSSRepository implements IITSSRepository {

    @Value("${env}")
    private String Environment;
    private RedisTemplate redisTemplate;
    HashOperations hashOperations;
    ZSetOperations zSetOperations;

    public ITSSRepository(RedisTemplate redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    @Override
    public ArrayList<ITSSRegistry> getITSSListByUserId(String UserId) {
        String pattern = "ITSS" + (Environment != "PR" ? Environment : "") + "*";
        Set<String> keys = getRelatedITSS(UserId);
        ArrayList<ITSSRegistry> result = new ArrayList<ITSSRegistry>();
        try {
            keys.forEach(s -> {
                        HashMap<String, String> content = (HashMap<String, String>) hashOperations.entries(s);
                        ITSSRegistry reg = new ITSSRegistry(content, s);
                        result.add(reg);
                    }
            );
            return result;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }


    @Override
    public ArrayList<ITSSRegistry> getITSSListByUserIdPaginated(String UserId, int from, int to) {
        Set<String> relatedKeys = getRelatedITSS(UserId);
        for (int iterator = from - 1; (iterator < to) && (iterator < relatedKeys.size()); iterator++) {
            System.out.println(relatedKeys.toArray()[iterator]);
        }
        return new ArrayList<ITSSRegistry>();
    }

    @Override
    public ArrayList<String> getAllITSSPaginated(int from, int to) throws JedisConnectionException {
        String pattern = "ITSS" + (Environment != "PR" ? Environment : "") + "*";
        ArrayList<String> keys = new ArrayList<String>();

        Set<String> allKeys = redisTemplate.keys(pattern);
        if (allKeys.size() > from) {

            for (int i = from - 1; ((i < to) && (i < allKeys.size())); i++) {
                String status = hashOperations.get(allKeys.toArray()[i].toString(), "Status").toString();
                keys.add(allKeys.toArray()[i].toString());
            }
        }
        return keys;
    }

    @Override
    public ArrayList<String> getAllITSSByPattern(String pattern) throws RedisSystemException {
       Set<String> keys =redisTemplate.keys("ITSS"+Environment+"-"+pattern+"*");
       ArrayList<String> l = new ArrayList<String>(keys);
       return l;
    }

    @Override
    public ITSSRegistry getITSSById(String Id) {
        HashMap<String, String> content = new HashMap<String, String>(hashOperations.entries(Id));
        ITSSRegistry reg = new ITSSRegistry(content, Id);
        return reg;
    }

    @Override
    public ArrayList<ITSSRegistry> getAllITSS()  {
        Set<String> keys = redisTemplate.keys("ITSS"+(Environment=="PR"?"":"DEV")+"*");
        ArrayList<ITSSRegistry> registries = new ArrayList<>();
        keys.forEach(key->{
            registries.add(new ITSSRegistry(new HashMap<>(hashOperations.entries(key)), key));
        });
        return  registries;
    }

    @Override
    public ArrayList<String> getAllITSSKeys() {
        Set<String> keys = redisTemplate.keys("ITSS"+(Environment=="PR"?"":"DEV")+"*");
        ArrayList<String> registries = new ArrayList<>(keys);
        return registries;
    }
    @Override
    public Set<String> getRelatedITSS(String user) {
        Set<String> keys = null;
        try {
            keys = zSetOperations.rangeByScore("ITSS.INDEX.USER." + user, 0, 1);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return keys;
    }

    @Override
    public String getITSSProperty(String ITSSId, String property) {
        Object result =  hashOperations.get(ITSSId,property);
        return (result == null)  ?"":result.toString();

    }

    @Override
    public Boolean updateITSSProperty(String ITSSId, String propertyName, String value) throws RedisSystemException {
        hashOperations.put(ITSSId,propertyName, value);
        return true;
    }

    @Override
    public Boolean addRegisterToITSSHistory(String ITSSId, HistoryITSSRegister register) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            return zSetOperations.add("HISTORY."+ITSSId , mapper.writeValueAsString(register).toString(),Double.parseDouble(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        }catch (Exception e){ return  false;}
    }

    @Override
    public List<Catalog1Value> getCatalog1Fields() {
        String catalog1Json = redisTemplate.opsForValue().get("CATALOG-1").toString();
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<Catalog1Value> values = new ArrayList<Catalog1Value>();
        try{
           values= objectMapper.readValue(catalog1Json, values.getClass());
            return values;
        }
        catch (Exception ex){

            return new ArrayList<Catalog1Value>();
        }

    }
}