package rgo.concurrency.examples5;

import rgo.concurrency.examples.annotations.Immutable;

import java.math.BigInteger;
import java.util.Arrays;

@Immutable
public class OneValueCache {

    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache() {
        lastNumber = null;
        lastFactors = null;
    }

    public OneValueCache(BigInteger i, BigInteger[] factors) {
        lastNumber = i;
        lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i)) {
            return null;
        }

        return Arrays.copyOf(lastFactors, lastFactors.length);
    }
}
