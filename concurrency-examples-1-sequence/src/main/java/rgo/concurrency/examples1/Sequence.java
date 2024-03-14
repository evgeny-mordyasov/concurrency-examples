package rgo.concurrency.examples1;

import rgo.concurrency.examples.annotations.ThreadSafe;

@ThreadSafe
public class Sequence {

    private int value;

    public synchronized int getNext() {
        return value++;
    }
}
