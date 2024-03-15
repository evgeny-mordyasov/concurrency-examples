package rgo.concurrency.examples5;

import rgo.concurrency.examples.annotations.ThreadSafe;
import rgo.concurrency.examples.annotations.UnnacceptablyPoorConcurrency;

import java.math.BigInteger;

@ThreadSafe
@UnnacceptablyPoorConcurrency
public class SynchronizedCachingServlet {

    private BigInteger lastNumber;
    private BigInteger[] lastFactors;

    public synchronized void doGet(ServletRequest request, ServletResponse response) {
        BigInteger i = extractFromRequest(request);

        if (i.equals(lastNumber)) {
            encodeIntoResponse(response, lastFactors);
        } else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(response, factors);
        }
    }

    private BigInteger extractFromRequest(ServletRequest request) {
        // code
        return BigInteger.ZERO;
    }

    private BigInteger[] factor(BigInteger i) {
        // code
        return new BigInteger[]{};
    }

    private void encodeIntoResponse(ServletResponse response, BigInteger[] factors) {
        // code
    }
}
