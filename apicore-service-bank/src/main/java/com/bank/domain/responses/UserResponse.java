package com.bank.domain.responses;

import java.util.Date;

public class UserResponse {

    private int id;
    private String username;
    private Date createdDate;
    private ClientResponse client;
    private AccountResponse account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ClientResponse getClient() {
        return client;
    }

    public void setClient(ClientResponse client) {
        this.client = client;
    }

    public AccountResponse getAccount() {
        return account;
    }

    public void setAccount(AccountResponse account) {
        this.account = account;
    }
}
