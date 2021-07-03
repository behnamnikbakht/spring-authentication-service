package com.bnbide.engine.uaa.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotEmpty(message = "identifier cannot be empty")
    @JsonProperty("identifier")
    private String identifier;

    @NotEmpty(message = "phone_number cannot be empty")
    @JsonProperty("phone_number")
    private String phoneNumber;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "identifier='" + identifier + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
