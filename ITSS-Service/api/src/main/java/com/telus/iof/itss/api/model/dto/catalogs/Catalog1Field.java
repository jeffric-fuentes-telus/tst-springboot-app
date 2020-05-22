package com.telus.iof.itss.api.model.dto.catalogs;

public class Catalog1Field{
    private String Label,Value;

    public Catalog1Field(String label, String value) {
        Label = label;
        Value = value;
    }

    public Catalog1Field() {
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
