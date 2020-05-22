package com.telus.iof.itss.api.model.entities.tmods;


public class TeamMember {
    String xId, Name;

    public TeamMember() {
    }

    public String getxId() {
        return xId;
    }

    public void setxId(String xId) {
        this.xId = xId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public TeamMember(String xId, String name) {
        this.xId = xId;
        Name = name;
    }
}
