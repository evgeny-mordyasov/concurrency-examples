package rgo.concurrency.examples11;

import rgo.concurrency.examples.annotations.ThreadSafe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@ThreadSafe
public class HiddenIteratorV2 {

    private final Set<Integer> set = Collections.synchronizedSet(new HashSet<>());

    public void add(Integer i) {
        set.add(i);
    }

    public void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            add(random.nextInt());
        }

        synchronized (set) {
            System.out.println("Добавлено десять элементов во множество. set=" + set);
        }
    }
}
