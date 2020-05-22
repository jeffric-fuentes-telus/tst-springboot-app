package com.telus.iof.itss.api.services;

import com.telus.iof.itss.api.model.entities.tmods.TeamMember;
import com.telus.iof.itss.api.repositories.interfaces.ITMODSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TMODSService {
    @Autowired
    ITMODSRepository tmodsRepository;

    public List<TeamMember> getTeamMembersByName(String hint){
        List<TeamMember> result;
        try{
            result = tmodsRepository.getTeamMemberByName(hint);
        }catch (Exception ex){
            result = new ArrayList<>();
        }
        return result;
    }
}
