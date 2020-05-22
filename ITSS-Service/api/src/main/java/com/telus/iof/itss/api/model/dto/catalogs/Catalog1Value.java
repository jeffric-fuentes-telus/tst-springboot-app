package com.telus.iof.itss.api.model.dto.catalogs;

import java.util.List;

public class Catalog1Value {
    private String Id, Label;
    private List<Catalog1Field> Options;

    public Catalog1Value(String id, String label, List<Catalog1Field> options) {
        Id = id;
        Label = label;
        Options = options;
    }

    public Catalog1Value() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public List<Catalog1Field> getOptions() {
        return Options;
    }

    public void setOptions(List<Catalog1Field> options) {
        Options = options;
    }
}
