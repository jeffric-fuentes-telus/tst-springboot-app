package com.telus.iof.itss.api.model.entities;

public class ITSSHATEOASEntity {
    String name, href, status;
    public ITSSHATEOASEntity(String name, String href, String status) {
        this.name = name;
        this.href = href;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
