package com.example.radeczkikrisztian_javafxrestclientdolgozat;

public class Car {

    private int id;
    private String carname;
    private String owner;
    private int carage;

    public Car(int id, String carname, String owner, int carage) {
        this.id = id;
        this.carname = carname;
        this.owner = owner;
        this.carage = carage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getCarage() {
        return carage;
    }

    public void setCarage(int carage) {
        this.carage = carage;
    }
}
