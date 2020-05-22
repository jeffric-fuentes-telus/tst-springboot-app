package com.telus.iof.itss.api.model.dto;

public class FieldUpdateRequest {
    private String UserId, Field, OldValue, NewValue;


    public FieldUpdateRequest(String userId, String field, String oldValue, String newValue) {
        UserId = userId;
        Field = field;
        OldValue = oldValue;
        NewValue = newValue;
    }
   public FieldUpdateRequest(){}

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getOldValue() {
        return OldValue;
    }

    public void setOldValue(String oldValue) {
        OldValue = oldValue;
    }

    public String getNewValue() {
        return NewValue;
    }

    public void setNewValue(String newValue) {
        NewValue = newValue;
    }
}
