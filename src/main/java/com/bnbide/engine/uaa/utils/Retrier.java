package com.bnbide.engine.uaa.utils;

import com.bnbide.engine.uaa.error.ExceptionCodes;
import com.bnbide.engine.uaa.error.UaaException;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class Retrier {

    public interface RetrierSupplier<T>{

        AtomicInteger roundValue = new AtomicInteger(0);

        T get() throws Exception;

        default int increment(){
            return roundValue.getAndIncrement();
        }

        default int round(){
            return roundValue.get();
        }

    }


    public static class RetrierUnit<T> {

        private int maxRetry;

        public RetrierUnit(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        public void retry(RetrierSupplier<T> supplier, Predicate<T> stopChecker, ObjectVerifier<T> consumer) throws UaaException {
            int i = 0;
            while (i < maxRetry) {
                T t;
                try {
                    t = supplier.get();
                } catch (Exception e) {
                    throw UaaException.build(ExceptionCodes.CODE_AUTHENTICATION_SERVER_REQUEST_ERROR, e.getMessage());
                }
                if (stopChecker.test(t)) {
                    consumer.accept(t);
                    return;
                }
                supplier.increment();
                ++i;
            }
            throw UaaException.build(ExceptionCodes.CODE_AUTHENTICATION_SERVER_REQUEST_ERROR, "Unable to connect to authorization server");
        }
    }

}
