package com.example.bloodpocket;

public class Versions {

    private String codeName, version,apilevel, description;
    private Boolean expandable;

    public Boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    public Versions(String codeName, String version, String apilevel, String description) {
        this.codeName = codeName;
        this.version = version;
        this.apilevel = apilevel;
        this.description = description;
        this.expandable = false;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApilevel() {
        return apilevel;
    }

    public void setApilevel(String apilevel) {
        this.apilevel = apilevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Versions{" +
                "codeName='" + codeName + '\'' +
                ", version='" + version + '\'' +
                ", apilevel='" + apilevel + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
