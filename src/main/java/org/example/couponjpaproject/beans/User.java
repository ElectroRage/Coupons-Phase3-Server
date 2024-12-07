package org.example.couponjpaproject.beans;

import org.example.couponjpaproject.login_manager.ClientType;

public class User {
    private String email;
    private String password;
    private String clientType;

    public User(String email, String password, String clientType) {
        this.email = email;
        this.password = password;
        this.clientType = clientType;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientType() {
        return clientType;
    }
}
