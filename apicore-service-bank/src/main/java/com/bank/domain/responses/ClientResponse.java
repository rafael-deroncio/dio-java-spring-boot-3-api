package com.bank.domain.responses;

import java.util.List;

public class ClientResponse {
    private String name;
    private String document;
    private String email;
    private List<TelephoneContactResponse> telephones;
    private AddressResponse address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TelephoneContactResponse> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<TelephoneContactResponse> telephones) {
        this.telephones = telephones;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }
}
