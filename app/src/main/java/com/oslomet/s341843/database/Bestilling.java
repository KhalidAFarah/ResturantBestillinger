package com.oslomet.s341843.database;

import java.util.Date;

public class Bestilling {
    private long id;
    private long resturantID;
    private long kontaktID;
    private Date date;

    public Bestilling(){}
    public Bestilling(long resturantID, long kontaktID, Date date){
        this.resturantID = resturantID;
        this.kontaktID = kontaktID;
        this.date = date;
    }
    public Bestilling(long id, long resturantID, long kontaktID, Date date){
        this.id = id;
        this.resturantID = resturantID;
        this.kontaktID = kontaktID;
        this.date = date;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public long getResturantID() {return resturantID;}
    public void setResturantID(long resturantID) {this.resturantID = resturantID;}

    public long getKontaktID() {return kontaktID;}
    public void setKontaktID(long kontaktID) {this.kontaktID = kontaktID;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public String toString(){
        return "ID: " + id + ", ResturantID: " + resturantID + ", KontaktID: " + kontaktID
                + ", Dato: " + date.toString();
    }
}
