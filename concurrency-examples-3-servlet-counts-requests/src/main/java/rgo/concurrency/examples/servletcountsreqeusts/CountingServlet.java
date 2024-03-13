package rgo.concurrency.examples.servletcountsreqeusts;

import rgo.concurrency.examples.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
public class CountingServlet {

    private final AtomicLong count = new AtomicLong();

    public void doGet(ServletRequest request, ServletResponse response) {
        // code
        count.incrementAndGet();
        // code
    }

    public long getCount() {
        return count.get();
    }
}
