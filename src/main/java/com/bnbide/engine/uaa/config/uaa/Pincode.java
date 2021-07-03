package com.bnbide.engine.uaa.config.uaa;

public class Pincode {

    private long ttl;

    private int length;

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Pincode{" +
                "ttl=" + ttl +
                ", length=" + length +
                '}';
    }
}
