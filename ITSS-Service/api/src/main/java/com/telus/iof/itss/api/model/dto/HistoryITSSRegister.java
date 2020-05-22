package com.telus.iof.itss.api.model.dto;

public class HistoryITSSRegister {
    private String dateChange, user, field, oldValue,newValue;

    public HistoryITSSRegister(String dateChange, String user, String field, String oldValue, String newValue) {
        this.dateChange = dateChange;
        this.user = user;
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getDateChange() {
        return dateChange;
    }

    public void setDateChanged(String dateChange) {
        this.dateChange = dateChange;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
