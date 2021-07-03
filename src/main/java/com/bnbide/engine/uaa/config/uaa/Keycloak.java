package com.bnbide.engine.uaa.config.uaa;

public class Keycloak {

    private String addr;

    private String username;

    private String password;

    private String realm;

    private String clientId;

    private String clientSecret;

    private String adminTokenUrl;

    private String jwksUri;

    private long timeout;

    private String defaultUserPassword;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAdminTokenUrl() {
        return adminTokenUrl;
    }

    public void setAdminTokenUrl(String adminTokenUrl) {
        this.adminTokenUrl = adminTokenUrl;
    }

    public String getJwksUri() {
        return jwksUri;
    }

    public void setJwksUri(String jwksUri) {
        this.jwksUri = jwksUri;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getDefaultUserPassword() {
        return defaultUserPassword;
    }

    public void setDefaultUserPassword(String defaultUserPassword) {
        this.defaultUserPassword = defaultUserPassword;
    }
}
