package com.bnbide.engine.uaa.error;

public class ExceptionCodes {

    public static final int CODE_INVALID_PINCODE = 1001;
    public static final int CODE_USER_DOES_NOT_EXIST = 1002;
    public static final int CODE_USER_ALREADY_EXISTS = 1003;
    public static final int CODE_AUTHENTICATION_SERVER_REQUEST_ERROR = 1004;
    public static final int CODE_UNAUTHORIZED_REQUEST = 1005;

    public static String errorMessage(int code){
        switch (code){
            case CODE_INVALID_PINCODE:
                return "invalid pincode";
            case CODE_USER_DOES_NOT_EXIST:
                return "user not found";
            case CODE_USER_ALREADY_EXISTS:
                return "user already exist";
            case CODE_AUTHENTICATION_SERVER_REQUEST_ERROR:
                return "error in retrieving data from authentication server";
            case CODE_UNAUTHORIZED_REQUEST:
                return "unauthorized request to authentication server";
            default:
                return "";
        }
    }

}
