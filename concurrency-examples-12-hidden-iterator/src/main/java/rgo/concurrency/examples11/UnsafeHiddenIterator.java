package rgo.concurrency.examples11;

import rgo.concurrency.examples.annotations.NotThreadSafe;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@NotThreadSafe
public class UnsafeHiddenIterator {

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

        System.out.println("Добавлено десять элементов во множество. set=" + set);
    }
}
