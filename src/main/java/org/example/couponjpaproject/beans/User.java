package org.example.couponjpaproject.beans;

import java.time.Instant;

public class User {
    private String email;
    private String password;
    private String clientType;
    private Instant lastRequest;


    public User(String clientType, Instant lastRequest) {
        this.clientType = clientType;
        this.lastRequest = lastRequest;
    }

    public User(String email, String password, String clientType) {
        this.email = email;
        this.password = password;
        this.clientType = clientType;
        this.lastRequest = Instant.now();
    }

    public User() {
    }

    public Instant getLastRequest() {
        return lastRequest;
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

    public void setLastRequest(Instant lastRequest) {
        this.lastRequest = lastRequest;
    }
}
