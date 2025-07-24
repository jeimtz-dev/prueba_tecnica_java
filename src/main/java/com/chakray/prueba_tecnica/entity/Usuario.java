package com.chakray.prueba_tecnica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


public class Usuario {
    private long id;
    private String email;
    private String name;
    private String password;
    private String created_at;
    private List<Address> addresses;

    public Usuario() {
    }

    public Usuario(long id, String email, String name, String password, String created_at, List<Address> addresses) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.created_at = created_at;
        this.addresses = addresses;
    }

    public long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getCreated_at() {
        return created_at;
    }
    public List<Address> getAddresses() {
        return addresses;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
