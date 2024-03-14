package rgo.concurrency.examples5;

import rgo.concurrency.examples.annotations.ThreadSafe;

import java.math.BigInteger;

@ThreadSafe
public class CachingServlet {

    private BigInteger lastNumber;
    private BigInteger[] lastFactors;

    public void doGet(ServletRequest request, ServletResponse response) {
        BigInteger i = extractFromRequest(request);
        BigInteger[] factors = null;

        synchronized (this) {
            if (i.equals(lastNumber)) {
                factors = lastFactors.clone();
            }
        }

        if (factors == null) {
            factors = factor(i);

            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }

        encodeIntoResponse(response, factors);
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
