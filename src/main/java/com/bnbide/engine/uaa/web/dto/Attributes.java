package com.bnbide.engine.uaa.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes {

    private List<String> phoneNumber;

    private List<String> device;

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Attributes phoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<String> getDevice() {
        return device;
    }

    public void setDevice(List<String> device) {
        this.device = device;
    }

    public Attributes device(List<String> device) {
        this.device = device;
        return this;
    }
}
