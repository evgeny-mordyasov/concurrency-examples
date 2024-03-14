package rgo.concurrency.examples5;

import rgo.concurrency.examples.annotations.NotThreadSafe;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

@NotThreadSafe
public class UnsafeCachingServlet {

    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    public void doGet(ServletRequest request, ServletResponse response) {
        BigInteger i = extractFromRequest(request);

        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(response, lastFactors.get());
        }
        else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
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
