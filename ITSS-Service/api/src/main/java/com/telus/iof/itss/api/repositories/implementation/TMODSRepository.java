package com.telus.iof.itss.api.repositories.implementation;

import com.telus.iof.itss.api.model.entities.tmods.TeamMember;
import com.telus.iof.itss.api.repositories.interfaces.ITMODSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TMODSRepository implements ITMODSRepository  {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<TeamMember> getTeamMemberByName(String hint) {
      List<TeamMember> members =  this.jdbcTemplate.query(
              "SELECT\n" +
                      "                case when \"enterprise_login_id\" is not null\n" +
                      "                then LOWER(\"enterprise_login_id\")\n" +
                      "                else LOWER(\"corp_network_login_id\")\n" +
                      "                end as xId,\n" +
                      "                case when preferred_given_name is not null\n" +
                      "                 then  given_name || ' ' || family_name end as name\n" +
                      "                 FROM team_member_extract\n" +
                      "                where LOWER(\n" +
                      "                  case when preferred_given_name is not null\n" +
                      "                 then preferred_given_name\n" +
                      "                else given_name end || ' ' || family_name\n" +
                      "                )like '%' || LOWER('"+hint+"') || '%'\n" +
                      "                and\n" +
                      "                 case when \"enterprise_login_id\" is not null\n" +
                      "                then LOWER(\"enterprise_login_id\")\n" +
                      "               else LOWER(\"corp_network_login_id\")  end is not null;",

              new RowMapper<TeamMember>() {
                public  TeamMember mapRow(ResultSet rs, int rowNumber) throws SQLException{
                    TeamMember tm = new TeamMember();
                    tm.setName(rs.getString("name"));
                    tm.setxId(rs.getString("xId"));
                    return  tm;
                }

        });
      return members;
    }
}
