package com.bnbide.engine.uaa.integration.impl;

import com.bnbide.engine.uaa.config.uaa.UaaConfig;
import com.bnbide.engine.uaa.integration.NotificationManager;
import com.bnbide.engine.uaa.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"prod"})
public class NotificationManagerImpl implements NotificationManager {

    private UaaConfig uaaConfig;

    @Autowired
    public NotificationManagerImpl(UaaConfig uaaConfig){
        this.uaaConfig = uaaConfig;
    }

    @Override
    public String createPincode() {
        return StringUtils.createRandomNumericString(uaaConfig.getPincode().getLength());
    }

    @Override
    public void sendPincode(String phoneNumber, String pincode) {
        // create a http request to an sms provider
    }

}
