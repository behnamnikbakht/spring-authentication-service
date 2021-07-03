package com.bnbide.engine.uaa.integration.impl;

import com.bnbide.engine.uaa.config.uaa.UaaConfig;
import com.bnbide.engine.uaa.error.ExceptionCodes;
import com.bnbide.engine.uaa.error.UaaException;
import com.bnbide.engine.uaa.integration.AuthenticationServer;
import com.bnbide.engine.uaa.web.dto.Attributes;
import com.bnbide.engine.uaa.web.dto.UserIdentifier;
import com.bnbide.engine.uaa.web.dto.UserInfo;
import com.bnbide.engine.uaa.utils.Retrier;
import com.bnbide.engine.uaa.web.dto.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bnbide.engine.uaa.integration.data.KeyCloakConstants.*;

@Component
public class KeycloakAuthenticationServer implements AuthenticationServer {

    private static final Logger log = LoggerFactory.getLogger(KeycloakAuthenticationServer.class);

    private String keycloakAddr;

    private String adminToken;

    private UaaConfig uaaConfig;

    private Retrier.RetrierUnit<HttpResponse<String>> retrier;

    private ObjectMapper objectMapper;

    public KeycloakAuthenticationServer(UaaConfig uaaConfig){
        this.uaaConfig = uaaConfig;
        this.objectMapper = new ObjectMapper();
        keycloakAddr = uaaConfig.getKeycloak().getAddr();
        retrier = new Retrier.RetrierUnit<>(2);
        Unirest.setTimeouts(uaaConfig.getKeycloak().getTimeout(), uaaConfig.getKeycloak().getTimeout());
        try {
            renewAdminToken();
        }
        catch (UaaException ex){
            log.error("keycloak error", ex);
        }
    }

    private void renewAdminToken() throws UaaException{
        log.info("renew admin token");
        try {
            HttpResponse<JsonNode> response = Unirest.post(keycloakAddr +
                    uaaConfig.getKeycloak().getAdminTokenUrl())
                    .field(USERNAME,uaaConfig.getKeycloak().getUsername())
                    .field(PASSWORD,uaaConfig.getKeycloak().getPassword())
                    .field(GRANT_TYPE,PASSWORD)
                    .field(CLIENT_ID,ADMIN_CLI)
                    .asJson();
            adminToken = (String) response.getBody().getObject().get(ACCESS_TOKEN);
        } catch (Exception e) {
            log.error("Error on get  admin token", e);
            throw UaaException.build(ExceptionCodes.CODE_AUTHENTICATION_SERVER_REQUEST_ERROR, "cannot obtain admin token");
        }
    }

    @Override
    public UserInfo getUser(UserIdentifier userIdentifier){
        List<UserInfo> list = findUsers(userIdentifier);
        if(list.size() == 0){
            log.debug("query user null result");
            return null;
        }
        for(UserInfo userInfo : list){
            String phoneNumber = null;
            Attributes attributes = userInfo.getAttributes();
            if(attributes != null){
                List<String> phoneNumbers = attributes.getPhoneNumber();
                if(phoneNumbers != null && phoneNumbers.size() > 0){
                    phoneNumber = phoneNumbers.get(0);
                }
            }
            UserIdentifier userIdentifier2 = new UserIdentifier()
                    .userId(userInfo.getId())
                    .phoneNumber(phoneNumber);
            if(userIdentifier2.sameAs(userIdentifier)){
                log.debug("query user result = {}", userInfo);
                return userInfo;
            }
        }
        return null;
    }

    @Override
    public List<UserInfo> findUsers(UserIdentifier userIdentifier) {
        String url;
        if(userIdentifier.hasPhoneNumber()) {
            url = String.format("%s/auth/admin/realms/%s/users?username=%s", keycloakAddr,
                    uaaConfig.getKeycloak().getRealm(), userIdentifier.getPhoneNumber());
        }
        else{
            url = String.format("%s/auth/admin/realms/%s/users/%s", keycloakAddr,
                    uaaConfig.getKeycloak().getRealm(), userIdentifier.getUserId());
        }

        log.debug("query user {}",userIdentifier);

        final StringBuilder sb = new StringBuilder();
        retrier.retry(new Retrier.RetrierSupplier<>() {
            @Override
            public HttpResponse<String> get() throws UnirestException {
                if (round() > 0) {
                    renewAdminToken();
                }
                return Unirest.get(url)
                        .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                        .header(AUTHORIZATION, String.format(AUTH_HEADER_PATTERN, BEARER, adminToken))
                        .asString();
            }
        }, response -> response.getStatus() != 401, (response) -> {
            sb.append(response.getBody());
        });
        if(sb.length() == 0){
            log.debug("query user null result");
            return null;
        }
        List<UserInfo> list;
        try {
            list = objectMapper.readValue(sb.toString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("cannot parse message from keycloak", e);
            throw UaaException.build(ExceptionCodes.CODE_AUTHENTICATION_SERVER_REQUEST_ERROR, e.getMessage());
        }
        return list;
    }

    @Override
    public boolean userExists(UserIdentifier userIdentifier) {
        return getUser(userIdentifier) != null;
    }

    @Override
    public void registerNewUser(UserIdentifier userIdentifier, UserInfo userInfo) {
        if(adminToken == null){
            renewAdminToken();
        }
        String username = userIdentifier.getPhoneNumber();
        JSONObject attrs = new JSONObject().put(ATTR_PHONE, username);
        JSONObject kcRequest = new JSONObject()
                .put(USERNAME, username)
                .put(ENABLED, TRUE)
                .put(ATTRIBUTES, attrs)
                .put(CREDENTIALS, new JSONArray().put(new JSONObject()
                        .put(TYPE, PASSWORD)
                        .put(VALUE, uaaConfig.getKeycloak().getDefaultUserPassword())
                        .put(TEMPORARY, false)));
        log.debug("kcrequest = {}", kcRequest);
        String url = keycloakAddr + "/auth/admin/realms/" + uaaConfig.getKeycloak().getRealm() + "/users";
        retrier.retry(new Retrier.RetrierSupplier<>() {
            @Override
            public HttpResponse<String> get() throws UnirestException {
                if (round() > 0) {
                    renewAdminToken();
                }
                return Unirest.post(url)
                        .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                        .header(AUTHORIZATION, String.format(AUTH_HEADER_PATTERN, BEARER, adminToken))
                        .body(kcRequest)
                        .asString();
            }
        }, response -> {

            log.info("Response from keyclock body {} ,status {} , headers {}, response {}.",response.getBody(),response.getStatus(),response.getHeaders(),response);
            return response.getStatus() != 401;
        }, (response) -> {
        });
    }

    @Override
    public Token renewToken(String username) {
        String url = String.format("%s/auth/realms/%s/protocol/openid-connect/token", keycloakAddr,
                uaaConfig.getKeycloak().getRealm());
        log.debug("fetch token : {}", username);

        final StringBuilder sb = new StringBuilder();

        retrier.retry(() -> Unirest.post(url)
                        .field(CLIENT_ID, uaaConfig.getKeycloak().getClientId())
                        .field(CLIENT_SECRET, uaaConfig.getKeycloak().getClientSecret())
                        .field(USERNAME, username)
                        .field(PASSWORD, uaaConfig.getKeycloak().getDefaultUserPassword())
                        .field(GRANT_TYPE, PASSWORD)
                        .asString()
                , response -> true, (response) -> sb.append(response.getBody()));

        Token token;
        try{
            token = objectMapper.readValue(sb.toString(), Token.class);
        }
        catch (Exception e){
            log.error("cannot parse message from keycloak", e);
            throw UaaException.build(ExceptionCodes.CODE_AUTHENTICATION_SERVER_REQUEST_ERROR, e.getMessage());
        }
        return token;
    }

    @Override
    public Token refreshToken(String refreshToken) {
        String url = String.format("%s/auth/realms/%s/protocol/openid-connect/token", keycloakAddr,
                uaaConfig.getKeycloak().getRealm());

        log.debug("refresh token : {}",refreshToken);

        final StringBuilder sb = new StringBuilder();

        retrier.retry(() -> Unirest.post(url)
                .field(CLIENT_ID, uaaConfig.getKeycloak().getClientId())
                .field(CLIENT_SECRET, uaaConfig.getKeycloak().getClientSecret())
                .field(GRANT_TYPE, REFRESH_TOKEN)
                .field(REFRESH_TOKEN, refreshToken)
                .asString(), response -> true, (response) -> {
            sb.append(response.getBody());
        });

        Token token;
        try{
            token = objectMapper.readValue(sb.toString(), Token.class);
        }
        catch (Exception e){
            log.error("cannot parse message from keycloak", e);
            throw UaaException.build(ExceptionCodes.CODE_AUTHENTICATION_SERVER_REQUEST_ERROR, e.getMessage());
        }
        return token;
    }

}
