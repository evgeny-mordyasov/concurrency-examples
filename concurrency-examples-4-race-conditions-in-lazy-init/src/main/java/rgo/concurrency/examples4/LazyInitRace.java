package rgo.concurrency.examples4;

import rgo.concurrency.examples.annotations.NotThreadSafe;

@NotThreadSafe
public class LazyInitRace {

    private Object instance;

    public Object getInstance() {
        if (instance == null) {
            instance = new Object();
        }

        return instance;
    }
}
