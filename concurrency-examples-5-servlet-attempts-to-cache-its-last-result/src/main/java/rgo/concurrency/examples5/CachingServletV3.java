package rgo.concurrency.examples5;

import rgo.concurrency.examples.annotations.ThreadSafe;

import java.math.BigInteger;

@ThreadSafe
public class CachingServletV3 {

    private volatile OneValueCache cache = new OneValueCache();

    public void doGet(ServletRequest request, ServletResponse response) {
        BigInteger i = extractFromRequest(request);
        BigInteger[] factors = cache.getFactors(i);

        if (factors == null) {
            factors = factor(i);
            cache = new OneValueCache(i, factors);
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
