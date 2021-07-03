package com.bnbide.engine.uaa.integration;

public interface NotificationManager {

    String createPincode();

    void sendPincode(String phoneNumber, String pincode);

}
