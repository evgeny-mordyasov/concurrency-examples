package rgo.concurrency.examples11;

import rgo.concurrency.examples.annotations.ThreadSafe;

@ThreadSafe
public class Counter {

    private long value;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("Переполнение счетчика.");
        }

        return ++value;
    }
}
