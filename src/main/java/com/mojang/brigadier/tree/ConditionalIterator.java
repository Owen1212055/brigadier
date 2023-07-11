package com.mojang.brigadier.tree;

import java.util.Iterator;
import java.util.function.Predicate;

class ConditionalIterator<T> implements Iterator<T> {

    private final Iterator<T> iterator;
    private final Predicate<T> predicate;
    private T next;

    public static <T> Iterable<T> of(Iterator<T> iterator, Predicate<T> predicate) {
        return () -> new ConditionalIterator<>(iterator, predicate);
    }

    ConditionalIterator(Iterator<T> iterator, Predicate<T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
        this.next = this.getNext();
    }

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public T next() {
        T result = this.next;
        this.next = this.getNext();
        return result;
    }

    private T getNext() {
        while (this.iterator.hasNext()) {
            T value = this.iterator.next();
            if (this.predicate.test(value)) {
                return value;
            }
        }
        return null;
    }
}
