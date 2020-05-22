package com.telus.iof.itss.api.controllers;

import com.telus.iof.itss.api.model.entities.tmods.TeamMember;
import com.telus.iof.itss.api.services.TMODSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
    @RequestMapping("/api/v1/teammembers")
public class TeamMemberController  {
    @Autowired
    TMODSService tmodsService;

    @GetMapping("/{hint}")
    ResponseEntity getTeamMembersByNameHint(@PathVariable String hint){
        List<TeamMember> tms= tmodsService.getTeamMembersByName(hint);
        return  ResponseEntity.ok(tms);
    }
}
