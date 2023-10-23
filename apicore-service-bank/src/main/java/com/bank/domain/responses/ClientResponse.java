package com.bank.domain.responses;

import java.util.List;

public class ClientResponse {
    private String name;
    private String document;
    private String email;
    private TelephoneContactResponse telephones;
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

    public TelephoneContactResponse getTelephones() {
        return telephones;
    }

    public void setTelephone(TelephoneContactResponse telephones) {
        this.telephones = telephones;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }
}
