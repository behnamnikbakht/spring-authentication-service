package com.bnbide.engine.uaa.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class RefreshTokenRequest {

    @NotEmpty(message = "refresh_token cannot be empty")
    @JsonProperty("refresh_token")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
