package rgo.concurrency.examples10;

import java.util.concurrent.atomic.AtomicReference;

public class Publisher {

    public Holder unsafeHolder;

    public static Holder safeHolder = new Holder(42);
    public volatile Holder safeHolder2;
    public AtomicReference<Holder> safeHolder3;
    public final Holder safeHolder4;
    private Holder safeHolder5;

    public Publisher() {
        unsafeHolder = new Holder(42);

        safeHolder = new Holder(42);
        safeHolder2 = new Holder(42);
        safeHolder3 = new AtomicReference<>(new Holder(42));
        safeHolder4 = new Holder(42);

        synchronized (this) {
            safeHolder5 = new Holder(42);
        }
    }

    public synchronized Holder getHolder() {
        return safeHolder5;
    }
}
