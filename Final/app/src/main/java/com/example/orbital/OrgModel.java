package com.example.orbital;

public class OrgModel {
    String NameEvent, Number, Org;

    public OrgModel(String NameEvent, String Number, String Org) {
        this.NameEvent = NameEvent;
        this.Number = Number;
        this.Org = Org;
    }

    public OrgModel() {
    }

    public  String getNameEvent() {
        return NameEvent;
    }

    public void setNameEvent(String NameEvent) {
        this.NameEvent = NameEvent;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getOrg() {
        return Org;
    }

    public void setOrg(String Org) {
        this.Org = Org;
    }
}
