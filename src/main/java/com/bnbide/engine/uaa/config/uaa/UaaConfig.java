package com.bnbide.engine.uaa.config.uaa;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "uaa")
public class UaaConfig {

    private Keycloak keycloak;

    private Pincode pincode;

    public Keycloak getKeycloak() {
        return keycloak;
    }

    public void setKeycloak(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public Pincode getPincode() {
        return pincode;
    }

    public void setPincode(Pincode pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "UaaConfig{" +
                "keycloak=" + keycloak +
                ", pincode=" + pincode +
                '}';
    }
}
