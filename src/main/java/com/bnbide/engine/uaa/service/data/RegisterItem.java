package com.bnbide.engine.uaa.service.data;

public class RegisterItem extends AuthItem{

    @Override
    public String toString() {
        return "RegisterItem{" +
                "pinCodeValue='" + pinCodeValue + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
