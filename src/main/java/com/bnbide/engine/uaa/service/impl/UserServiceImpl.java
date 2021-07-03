package com.bnbide.engine.uaa.service.impl;

import com.bnbide.engine.uaa.config.uaa.UaaConfig;
import com.bnbide.engine.uaa.error.ExceptionCodes;
import com.bnbide.engine.uaa.error.UaaException;
import com.bnbide.engine.uaa.integration.AuthenticationServer;
import com.bnbide.engine.uaa.integration.NotificationManager;
import com.bnbide.engine.uaa.web.dto.Attributes;
import com.bnbide.engine.uaa.web.dto.UserIdentifier;
import com.bnbide.engine.uaa.web.dto.UserInfo;
import com.bnbide.engine.uaa.repository.InMemoryRepo;
import com.bnbide.engine.uaa.service.UserService;
import com.bnbide.engine.uaa.service.data.AuthItem;
import com.bnbide.engine.uaa.service.data.AuthKey;
import com.bnbide.engine.uaa.service.data.LoginItem;
import com.bnbide.engine.uaa.service.data.RegisterItem;
import com.bnbide.engine.uaa.web.dto.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UaaConfig uaaConfig;

    private InMemoryRepo<AuthKey, AuthItem> inMemoryRepo;

    private NotificationManager notificationManager;

    private AuthenticationServer authenticationServer;

    @Autowired
    public UserServiceImpl(UaaConfig uaaConfig,
                           InMemoryRepo<AuthKey, AuthItem> inMemoryRepo,
                           NotificationManager notificationManager,
                           AuthenticationServer authenticationServer){
        this.uaaConfig = uaaConfig;
        this.inMemoryRepo = inMemoryRepo;
        this.notificationManager = notificationManager;
        this.authenticationServer = authenticationServer;
    }

    @Override
    public void login(String identifier, String phoneNumber) {
        log.debug("login identifier = {}, phoneNumber = {}", identifier, phoneNumber);

        if(!authenticationServer.userExists(new UserIdentifier().phoneNumber(phoneNumber))){
            log.error("login user not found {}", phoneNumber);
            throw UaaException.build(ExceptionCodes.CODE_USER_DOES_NOT_EXIST);
        }

        String pinCode = notificationManager.createPincode();

        log.debug("login generated phoneNumber = {}, pinCode = {}", phoneNumber, pinCode);

        inMemoryRepo.set(createAuthKey(identifier), new LoginItem()
                .type(AuthItem.AuthItemType.LOGIN)
                .pinCodeValue(pinCode)
                .phoneNumber(phoneNumber));

        notificationManager.sendPincode(phoneNumber, pinCode);
    }

    @Override
    public void register(String identifier, String phoneNumber) {
        log.debug("register identifier = {}, phoneNumber = {}", identifier, phoneNumber);

        if(authenticationServer.userExists(new UserIdentifier().phoneNumber(phoneNumber))){
            log.error("register user already exists {}", phoneNumber);
            throw UaaException.build(ExceptionCodes.CODE_USER_ALREADY_EXISTS);
        }

        String pinCode = notificationManager.createPincode();

        log.debug("register generated phoneNumber = {}, pinCode = {}", phoneNumber, pinCode);

        inMemoryRepo.set(createAuthKey(identifier), new RegisterItem()
                .type(AuthItem.AuthItemType.REGISTER)
                .pinCodeValue(pinCode)
                .phoneNumber(phoneNumber));

        notificationManager.sendPincode(phoneNumber, pinCode);
    }

    @Override
    public Token authenticate(String identifier, String pinCode) {
        log.debug("authenticate identifier = {}, pinCode = {}", identifier, pinCode);

        AuthItem authItem = inMemoryRepo.get(createAuthKey(identifier));

        log.debug("authenticate authItem = {}", authItem);

        if(authItem == null){
            log.error("authenticate pinCode not found for identifier {}", identifier);
            throw UaaException.build(ExceptionCodes.CODE_INVALID_PINCODE);
        }

        if(!authItem.getPinCodeValue().equals(pinCode)){
            log.error("authenticate invalid pinCode for identifier {}", identifier);
            throw UaaException.build(ExceptionCodes.CODE_INVALID_PINCODE);
        }

        UserIdentifier userIdentifier = new UserIdentifier().phoneNumber(authItem.getPhoneNumber());

        if(authItem.getType() == AuthItem.AuthItemType.LOGIN) {
            UserInfo userInfo = authenticationServer.getUser(userIdentifier);
            if (userInfo == null) {
                log.error("login user not found {}", authItem.getPhoneNumber());
                throw UaaException.build(ExceptionCodes.CODE_USER_DOES_NOT_EXIST);
            }
        }
        else if(authItem.getType() == AuthItem.AuthItemType.REGISTER) {
            UserInfo newUser = new UserInfo()
                    .username(userIdentifier.getPhoneNumber())
                    .enabled(true)
                    .attributes(
                            new Attributes()
                            .device(Arrays.asList(identifier))
                            .phoneNumber(Arrays.asList(userIdentifier.getPhoneNumber()))
                    );
            authenticationServer.registerNewUser(userIdentifier, newUser);
        }
        return authenticationServer.renewToken(userIdentifier.getPhoneNumber());
    }

    @Override
    public Token refreshToken(String refreshToken) {
        log.debug("refreshToken refreshToken = {}", refreshToken);
        return authenticationServer.refreshToken(refreshToken);
    }

    private AuthKey createAuthKey(String identifier){
        return new AuthKey().identifier(identifier).type("user");
    }

}
