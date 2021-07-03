package com.bnbide.engine.uaa.service;

import com.bnbide.engine.uaa.web.dto.Token;

public interface UserService {

    void login(String identifier, String phoneNumber);

    void register(String identifier, String phoneNumber);

    Token authenticate(String identifier, String pinCode);

}
