package com.app.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private String username;
    private String password;
    private String status;

    //default constructor for JSON parsing
    public JwtRequest(){

    }

    public JwtRequest(String username, String password, boolean flag){
        this.setUsername(username);
        this.setPassword(password);
        this.setStatus(status);
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
