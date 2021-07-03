package com.bnbide.engine.uaa.service.data;

import java.io.Serializable;
import java.util.Objects;

public class AuthKey implements Serializable {

    private String type;

    private String identifier;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AuthKey type(String type) {
        this.type = type;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public AuthKey identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthKey authKey = (AuthKey) o;
        return type.equals(authKey.type) &&
                identifier.equals(authKey.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, identifier);
    }

    @Override
    public String toString() {
        return "AuthKey{" +
                "type='" + type + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
