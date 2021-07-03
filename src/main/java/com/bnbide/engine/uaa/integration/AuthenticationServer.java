package com.bnbide.engine.uaa.integration;

import com.bnbide.engine.uaa.web.dto.UserIdentifier;
import com.bnbide.engine.uaa.web.dto.UserInfo;
import com.bnbide.engine.uaa.web.dto.Token;

import java.util.List;

public interface AuthenticationServer {

    UserInfo getUser(UserIdentifier userIdentifier);

    List<UserInfo> findUsers(UserIdentifier userIdentifier);

    boolean userExists(UserIdentifier userIdentifier);

    void registerNewUser(UserIdentifier userIdentifier, UserInfo userInfo);

    Token renewToken(String username);

    Token renewRefreshToken(String refreshToken);

}
