package com.bnbide.engine.uaa.service.data;

import java.io.Serializable;
import java.util.Objects;

public class AuthItem implements Serializable {

    protected String pinCodeValue;

    protected String phoneNumber;

    private AuthItemType type;

    public AuthItemType getType() {
        return type;
    }

    public void setType(AuthItemType type) {
        this.type = type;
    }

    public AuthItem type(AuthItemType type) {
        this.type = type;
        return this;
    }

    public String getPinCodeValue() {
        return pinCodeValue;
    }

    public void setPinCodeValue(String pinCodeValue) {
        this.pinCodeValue = pinCodeValue;
    }

    public AuthItem pinCodeValue(String pinCodeValue) {
        this.pinCodeValue = pinCodeValue;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AuthItem phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String toString() {
        return "AuthItem{" +
                "pinCodeValue='" + pinCodeValue + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthItem authItem = (AuthItem) o;
        return pinCodeValue.equals(authItem.pinCodeValue) &&
                phoneNumber.equals(authItem.phoneNumber) &&
                type == authItem.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pinCodeValue, phoneNumber, type);
    }

    public enum AuthItemType{
        LOGIN,
        REGISTER;
    }
}
