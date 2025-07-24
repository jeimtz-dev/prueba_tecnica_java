package com.chakray.prueba_tecnica.entity;


public class Address {

    private long id;
    private String name;
    private String street;
    private String country_code;

    public Address(){

    }
    public Address(long id, String name, String street, String country_code) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.country_code = country_code;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getStreet() {
        return street;
    }
    public String getCountry_code() {
        return country_code;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}
