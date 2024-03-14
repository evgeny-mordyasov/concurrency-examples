package rgo.concurrency.examples3;

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
