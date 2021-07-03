package com.bnbide.engine.uaa.integration.impl;

import com.bnbide.engine.uaa.integration.NotificationManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"!prod"})
public class NotificationManagerSimpleImpl implements NotificationManager {

    @Override
    public String createPincode() {
        return "1234";
    }

    @Override
    public void sendPincode(String phoneNumber, String pincode) {
        // do nothing
    }

}
