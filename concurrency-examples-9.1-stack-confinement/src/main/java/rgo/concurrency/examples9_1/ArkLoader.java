package rgo.concurrency.examples9_1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ArkLoader {

    private final Ark ark;

    public ArkLoader(Ark ark) {
        this.ark = ark;
    }

    public int loadTheArk(Collection<Animal> candidates) {
        Set<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;

        // не дайте animals ускользнуть!
        animals = new HashSet<>(candidates);

        for (Animal animal : animals) {
            if (candidate == null || !candidate.isPotentialMate(animal)) {
                candidate = animal;
            } else {
                ark.load(new AnimalPair(candidate, animal));
                ++numPairs;
                candidate = null;
            }
        }

        return numPairs;
    }
}
