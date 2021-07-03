package com.bnbide.engine.uaa.utils;

import java.util.function.Consumer;

@FunctionalInterface
public interface ObjectVerifier<T> extends Consumer<T> {

    @Override
    default void accept(final T t) {
        try {
            acceptThrows(t);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T t) throws Exception;

}
