package com.bnbide.engine.uaa.repository;

public interface InMemoryRepo<K,V> {

    void set(K key, V value);

    V get(K key);

}
