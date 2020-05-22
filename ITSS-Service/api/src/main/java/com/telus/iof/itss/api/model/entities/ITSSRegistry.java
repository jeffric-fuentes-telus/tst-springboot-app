package com.telus.iof.itss.api.model.entities;

import java.util.HashMap;
import java.util.Map;

public class ITSSRegistry {
    public HashMap<String, String> content;
    public String name;

    public String getName() {
        return name;
    }

    public ITSSRegistry(HashMap<String, String> content, String name) {
        this.content = content;
        this.name = name;
    }

    public ITSSRegistry() {
    }

    public void setName(String name) {
        this.name = name;
    }
    public HashMap<String, String> getContent() {
        return content;
    }

    public void setContent(HashMap<String, String> content) {
        this.content = content;
    }
}
