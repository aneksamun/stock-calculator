package com.nutmeg.stockcalculator.domain.validation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public final class ValidationErrors implements Iterable<String> {

    private final List<String> errors = new LinkedList<>();

    public void add(ValidationError error, Object... args) {
        errors.add(error.getErrorMessage(args));
    }

    public void add(ValidationError error) {
        errors.add(error.getErrorMessage());
    }

    public void compose(ValidationErrors other) {
        errors.addAll(other.errors);
    }

    public boolean any() {
        return !errors.isEmpty();
    }

    @Override
    public Iterator<String> iterator() {
        return errors.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        errors.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return errors.spliterator();
    }

    @Override
    public String toString() {
        return String.join("; ", errors);
    }
}
