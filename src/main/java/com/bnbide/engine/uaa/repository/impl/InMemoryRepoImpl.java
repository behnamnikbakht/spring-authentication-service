package com.bnbide.engine.uaa.repository.impl;

import com.bnbide.engine.uaa.config.uaa.UaaConfig;
import com.bnbide.engine.uaa.repository.InMemoryRepo;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class InMemoryRepoImpl<K,V> implements InMemoryRepo<K,V> {

    private Cache<K,V> map;

    @Autowired
    public InMemoryRepoImpl(UaaConfig uaaConfig){
        map = Caffeine.newBuilder()
                .expireAfterWrite(uaaConfig.getPincode().getTtl(), TimeUnit.SECONDS)
                .build();
    }


    @Override
    public void set(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V get(K key) {
        return map.getIfPresent(key);
    }

}
