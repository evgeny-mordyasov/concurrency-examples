package rgo.concurrency.examples.sequence;

import rgo.concurrency.examples.annotations.NotThreadSafe;

@NotThreadSafe
public class UnsafeSequence {

    private int value;

    public int getNext() {
        return value++;
    }
}
