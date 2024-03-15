package rgo.concurrency.examples8;

import rgo.concurrency.examples.annotations.ThreadSafe;

@ThreadSafe
public class MutableInteger {

    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized void set(int value) {
        this.value = value;
    }
}
