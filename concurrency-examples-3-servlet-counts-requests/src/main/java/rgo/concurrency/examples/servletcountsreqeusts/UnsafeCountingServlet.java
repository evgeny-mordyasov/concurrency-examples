package rgo.concurrency.examples.servletcountsreqeusts;

import rgo.concurrency.examples.annotations.NotThreadSafe;

@NotThreadSafe
public class UnsafeCountingServlet {

    private long count;

    public void doGet(ServletRequest request, ServletResponse response) {
        // code
        ++count;
        // code
    }

    public long getCount() {
        return count;
    }
}
