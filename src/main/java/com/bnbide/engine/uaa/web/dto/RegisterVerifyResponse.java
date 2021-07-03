package com.bnbide.engine.uaa.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterVerifyResponse {

    @JsonProperty("token")
    private Token token;

    public RegisterVerifyResponse token(Token token){
        this.token = token;
        return this;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegisterVerifyResponse{" +
                "token=" + token +
                '}';
    }
}
