package org.example.couponjpaproject.beans;

import org.example.couponjpaproject.services.ClientServices;

import java.time.Instant;

public class User {
    private String email;
    private String password;
    private String clientType;
    private Instant lastRequest;
    private ClientServices client;



    public User(String email, String password, String clientType) {
        this.email = email;
        this.password = password;
        this.clientType = clientType;
        this.lastRequest = Instant.now();
    }

    public User(ClientServices client, Instant lastRequest, String clientType) {
        this.client = client;
        this.lastRequest = lastRequest;
        this.clientType = clientType;
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
