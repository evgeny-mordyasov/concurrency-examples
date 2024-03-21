package rgo.concurrency.examples11;

import rgo.concurrency.examples.annotations.ThreadSafe;

@ThreadSafe
public class PrivateLock {

    private final Object myLock = new Object();

    void someMethod() {
        synchronized(myLock) {
            // Обратиться и изменить состояние виджета
        }
    }
}
