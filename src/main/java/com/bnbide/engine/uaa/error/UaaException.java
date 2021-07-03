package com.bnbide.engine.uaa.error;

public class UaaException extends RuntimeException{

    private int code;

    public UaaException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static UaaException build(int code){
        return new UaaException(code, ExceptionCodes.errorMessage(code));
    }

    public static UaaException build(int code, String message) {
        return new UaaException(code, message);
    }

    public int getCode() {
        return code;
    }
}
