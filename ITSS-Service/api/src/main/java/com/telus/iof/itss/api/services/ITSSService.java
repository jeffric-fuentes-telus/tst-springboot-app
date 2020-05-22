package com.telus.iof.itss.api.services;

import com.telus.iof.itss.api.model.dto.HistoryITSSRegister;
import com.telus.iof.itss.api.model.dto.ITSSUserHTTPRequest;
import com.telus.iof.itss.api.model.dto.catalogs.Catalog1Value;
import com.telus.iof.itss.api.model.entities.ITSSHATEOASEntity;
import com.telus.iof.itss.api.model.entities.ITSSRegistry;
import com.telus.iof.itss.api.model.entities.tmods.TeamMember;
import com.telus.iof.itss.api.repositories.interfaces.IITSSRepository;
import com.telus.iof.itss.api.repositories.interfaces.ITMODSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ITSSService {
    @Autowired
    IITSSRepository repository;
    @Value("${env}")
    private String Environment;

    public ArrayList<ITSSRegistry> getAll(){
        try{
            return repository.getAllITSS();
        }catch (Exception ex){
            return new ArrayList<>();
        }
    }

    public ArrayList<ITSSRegistry> getITSSRelatedToUserPaginated(ITSSUserHTTPRequest request, Integer from, Integer to) {
        if (from != null && to != null) {
            return repository.getITSSListByUserIdPaginated(request.getUserId(), from.intValue(), to.intValue());
        } else {
            if (request.getUserId() != null) {
                try {
                    return repository.getITSSListByUserId(request.getUserId());
                } catch (Exception ex) {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    public ArrayList<ITSSHATEOASEntity> getITSSByPattern(String pattern){
        String internalPattern = "ITSS" + (Environment != "PR" ? Environment : "") + "*";
        if(pattern.contains(internalPattern) || pattern.contains("ITSS")){
            pattern.split("-");
          ArrayList<String> l = repository.getAllITSSByPattern(  pattern.split("-")[1]);
           ArrayList<ITSSHATEOASEntity> findedKeys = new ArrayList<ITSSHATEOASEntity>();
           l.forEach(key->{
               String status = repository.getITSSProperty(key, "Status");
               findedKeys.add(new ITSSHATEOASEntity(key,"/api/v1/ITSS/"+key,status));
           });
           return  findedKeys;
        }
        return new ArrayList<ITSSHATEOASEntity>();
    }

    public ArrayList<ITSSHATEOASEntity> GetITSSPaginated(Integer from, Integer to) {
        ArrayList<String> keys = repository.getAllITSSPaginated(from, to);
        ArrayList<ITSSHATEOASEntity> itssEntities = new ArrayList<ITSSHATEOASEntity>();
        keys.forEach(key -> {
            itssEntities.add(new ITSSHATEOASEntity(key, "/api/v1/ITSS/" + key , repository.getITSSProperty(key, "Status")));
        });
        return itssEntities;
    }

    public ArrayList<ITSSRegistry> getRelatedITSSByUser(ITSSUserHTTPRequest request) {
        if (request.getUserId() != null) {
            try {
                return repository.getITSSListByUserId(request.getUserId());
            } catch (Exception ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    public ArrayList<ITSSHATEOASEntity> getRelatedITSSHateoasByUser( ITSSUserHTTPRequest request){
        if (request.getUserId() != null) {
            try {
                Set<String> keys = repository.getRelatedITSS(request.getUserId());
                ArrayList<ITSSHATEOASEntity> itssEntities = new ArrayList<ITSSHATEOASEntity>();
                keys.forEach(key -> {
                    itssEntities.add(new ITSSHATEOASEntity(key, "/api/v1/ITSS/" + key , repository.getITSSProperty(key, "Status")));
                });
                return itssEntities;
            } catch (Exception ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    public ITSSRegistry getITSSById(String Id) {
        if (Id.equals("") || Id.equals(null)) {
            return null;
        } else {

            return repository.getITSSById(Id);
        }
    }

    public List<ITSSHATEOASEntity>getAllITSSHateoas(){
        ArrayList<String> keys = repository.getAllITSSKeys();
        ArrayList<ITSSHATEOASEntity> registries = new ArrayList<>();
        keys.forEach(key->{
           registries.add( new ITSSHATEOASEntity(key,"/api/v1/ITSS/", repository.getITSSProperty(key,"Status") ));
        });
        return registries;
    }

    public boolean updateITSSProperty(String userId, String itssId, String propertyName, String oldValue, String newValue){
        repository.updateITSSProperty(itssId,propertyName, newValue);
        HistoryITSSRegister register = new HistoryITSSRegister(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString(), userId, propertyName, oldValue, newValue);
       return repository.addRegisterToITSSHistory(itssId, register);
    }
    public List<Catalog1Value> getCatalog1(){
        List<Catalog1Value> catalog= repository.getCatalog1Fields();
        //generate exception if empty
       return catalog;
    }
}

