package com.telus.iof.itss.api.repositories.interfaces;

import com.telus.iof.itss.api.model.entities.tmods.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

public interface ITMODSRepository {
//    @Query(value = "SELECT  " +
//            "case when \"ENTERPRISE_LOGIN_ID\" is not null " +
//            "then LOWER(\"ENTERPRISE_LOGIN_ID\") " +
//            "else LOWER(\"CORP_NETWORK_LOGIN_ID\") " +
//            "end as xId, " +
//            "case when preferred_given_name is not null " +
//            " then preferred_given_name " +
//            " else given_name || ' ' || family_name end as name" +
//            " FROM public.\"TEAM_MEMBER_EXTRACT\" " +
//            "where LOWER( " +
//            "  case when preferred_given_name is not null " +
//            " then preferred_given_name  " +
//            " else given_name end || ' ' || family_name\n" +
//            " )like '%' || LOWER(':hint') || '%'  " +
//            " and \n" +
//            " case when \"ENTERPRISE_LOGIN_ID\" is not null " +
//            " then LOWER(\"ENTERPRISE_LOGIN_ID\")" +
//            " " +
//            " else LOWER(\"CORP_NETWORK_LOGIN_ID\")  end is not null \n" +
//            " ;" +
//            "", nativeQuery = true)
    List<TeamMember> getTeamMemberByName (String hint);
}
