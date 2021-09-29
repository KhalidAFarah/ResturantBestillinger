package com.oslomet.s341843.database;

public class Resturant {
    private long id;
    private String name;
    private String adress;
    private String tlf;
    private String type;

    public Resturant(){}
    public Resturant(String name, String adress, String tlf, String type){
        this.name = name;
        this.adress = adress;
        this.tlf = tlf;
        this.type = type;
    }
    public Resturant(long id, String name, String adress, String tlf, String type){
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.tlf = tlf;
        this.type = type;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getAdress() {return adress;}
    public void setAdress(String adress) {this.adress = adress;}

    public String getTlf() {return tlf;}
    public void setTlf(String tlf) {this.tlf = tlf;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String toString(){
        return "ID: " + id + ", Navn: " + name + ", Adresse: " + adress + ", Tlf: " + tlf + ", Type: " + type;
    }
}
