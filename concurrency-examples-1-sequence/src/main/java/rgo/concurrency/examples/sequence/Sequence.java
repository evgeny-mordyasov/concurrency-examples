package rgo.concurrency.examples.sequence;

import rgo.concurrency.examples.annotations.ThreadSafe;

@ThreadSafe
public class Sequence {

    private int value;

    public synchronized int getNext() {
        return value++;
    }
}
