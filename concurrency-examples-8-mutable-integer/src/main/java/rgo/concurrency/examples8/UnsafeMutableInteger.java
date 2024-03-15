package rgo.concurrency.examples8;

import rgo.concurrency.examples.annotations.NotThreadSafe;

@NotThreadSafe
public class UnsafeMutableInteger {

    private int value;

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }
}
