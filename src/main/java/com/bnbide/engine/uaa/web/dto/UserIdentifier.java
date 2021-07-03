package com.bnbide.engine.uaa.web.dto;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class UserIdentifier {

    private String phoneNumber;

    private String userId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserIdentifier phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserIdentifier userId(String userId) {
        this.userId = userId;
        return this;
    }

    public boolean hasPhoneNumber() {
        return !StringUtils.isEmpty(phoneNumber);
    }

    public boolean sameAs(UserIdentifier that) {
        return Objects.equals(phoneNumber, that.phoneNumber) ||
                Objects.equals(userId, that.userId);
    }
}
