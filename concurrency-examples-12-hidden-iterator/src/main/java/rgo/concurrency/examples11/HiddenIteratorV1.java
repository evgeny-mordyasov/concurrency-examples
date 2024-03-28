package rgo.concurrency.examples11;

import rgo.concurrency.examples.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@ThreadSafe
public class HiddenIteratorV1 {

    private final Set<Integer> set = new HashSet<>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            add(random.nextInt());
        }

        synchronized (this) {
            System.out.println("Добавлено десять элементов во множество. set=" + set);
        }
    }
}
