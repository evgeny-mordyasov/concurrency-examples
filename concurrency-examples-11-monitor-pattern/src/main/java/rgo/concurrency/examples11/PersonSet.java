package rgo.concurrency.examples11;

import rgo.concurrency.examples.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class PersonSet {

    private final Set<Person> persons = new HashSet<>();

    public synchronized void addPerson(Person person) {
        persons.add(person);
    }

    public synchronized boolean containsPerson(Person person) {
        return persons.contains(person);
    }
}
