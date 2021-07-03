package com.bnbide.engine.uaa.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;

@ControllerAdvice
public class ExceptionTranslator{

    @ExceptionHandler
    public ResponseEntity<Problem> handleRegistrationException(UaaException ex, NativeWebRequest request) {
        return ResponseEntity.status(toStatusCode(ex)).body(toProblem(ex));
    }

    private Problem toProblem(UaaException ex) {
        return new Problem().code(ex.getCode()).message(ex.getMessage());
    }

    private int toStatusCode(UaaException ex) {
        switch (ex.getCode()){
            case ExceptionCodes.CODE_INVALID_PINCODE:
                return HttpStatus.BAD_REQUEST.value();
            case ExceptionCodes.CODE_USER_ALREADY_EXISTS:
                return HttpStatus.BAD_REQUEST.value();
            case ExceptionCodes.CODE_USER_DOES_NOT_EXIST:
                return HttpStatus.NOT_FOUND.value();
            default:
                return HttpStatus.BAD_REQUEST.value();
        }
    }

}
