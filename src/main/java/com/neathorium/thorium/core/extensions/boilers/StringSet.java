package com.neathorium.thorium.core.extensions.boilers;

import com.neathorium.thorium.core.extensions.interfaces.IEmptiableCollection;
import com.neathorium.thorium.core.extensions.namespaces.EmptiableFunctions;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.extensions.namespaces.SizableFunctions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StringSet implements Set<String>, IEmptiableCollection {
    public final Set<String> set;

    public StringSet(Set<String> set) {
        this.set = set;
    }

    @Override
    public int size() {
        return SizableFunctions.size(set::size);
    }

    @Override
    public boolean isEmpty() {
        return EmptiableFunctions.isEmpty(set);
    }

    @Override
    public boolean isNullOrEmpty() {
        return EmptiableFunctions.isNullOrEmpty(set);
    }

    @Override
    public boolean isNotNullAndNonEmpty() {
        return EmptiableFunctions.isNotNullAndNonEmpty(set);
    }

    @Override
    public boolean hasOnlyNonNullValues() {
        return EmptiableFunctions.hasOnlyNonNullValues(set);
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<String> iterator() {
        return set.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        set.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean add(String s) {
        return set.add(s);
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        return set.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return set.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return set.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super String> filter) {
        return set.removeIf(filter);
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public boolean equals(Object o) {
        return set.equals(o);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public Spliterator<String> spliterator() {
        return set.spliterator();
    }

    @Override
    public Stream<String> stream() {
        return set.stream();
    }

    @Override
    public Stream<String> parallelStream() {
        return set.parallelStream();
    }

    @Override
    public boolean isNull() {
        return NullableFunctions.isNull(set);
    }

    @Override
    public boolean isNotNull() {
        return NullableFunctions.isNotNull(set);
    }
}
