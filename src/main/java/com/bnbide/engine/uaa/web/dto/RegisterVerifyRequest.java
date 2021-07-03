package com.bnbide.engine.uaa.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class RegisterVerifyRequest {

    @NotEmpty(message = "identifier cannot be empty")
    @JsonProperty("identifier")
    private String identifier;

    @NotEmpty(message = "pin_code cannot be empty")
    @JsonProperty("pin_code")
    private String pinCode;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "RegisterVerifyRequest{" +
                "identifier='" + identifier + '\'' +
                ", pinCode='" + pinCode + '\'' +
                '}';
    }
}
