package com.example.mietapp;

import android.content.Context;

public class CustomerModel {
    private String id;
    private String Name;
    private String Handy;
    private String Von;
    private String Bis;
    private String Adresse;
    private String Bemerkung;

    public CustomerModel(String id, String name, String handy, String adresse, String von, String bis, String bemerkung) {
        this.id = id;
        this.Name = name;
        this.Handy = handy;
        this.Adresse = adresse;
        this.Von = von;
        this.Bis = bis;
        this.Bemerkung = bemerkung;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Handy='" + Handy + '\'' +
                ", Adresse='" + Adresse + '\'' +
                ", Von='" + Von + '\'' +
                ", Bis='" + Bis + '\'' +
                ", Bemerkung='" + Bemerkung + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHandy() {
        return Handy;
    }

    public void setHandy(String handy) {
        Handy = handy;
    }

    public String getVon() {
        return Von;
    }

    public void setVon(String von) {
        Von = von;
    }

    public String getBis() {
        return Bis;
    }

    public void setBis(String bis) {
        Bis = bis;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getBemerkung() {
        return Bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        Bemerkung = bemerkung;
   }
}
