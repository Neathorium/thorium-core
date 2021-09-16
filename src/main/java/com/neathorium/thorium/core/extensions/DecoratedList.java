package com.neathorium.thorium.core.extensions;

import com.neathorium.thorium.core.extensions.interfaces.IExtendedList;
import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.predicates.AmountPredicates;
import com.neathorium.thorium.core.extensions.namespaces.EmptiableFunctions;
import com.neathorium.thorium.core.extensions.namespaces.ExtendedListFunctions;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.extensions.namespaces.SizableFunctions;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class DecoratedList<T> implements IExtendedList<T> {
    private final String type;
    public final List<T> list;

    public DecoratedList(List<T> list, String type) {
        this.list = list;
        this.type = type;
    }

    @Override
    public int size() {
        return SizableFunctions.size(list::size);
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        list.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T element) {
        return list.add(element);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    public boolean addNullSafe(T element) {
        return isNotNull() && NullableFunctions.isNotNull(element) && list.add(element);
    }

    public boolean addAllNullSafe(Collection<? extends T> c) {
        return ExtendedListFunctions.addAllCondition(list, c) && list.addAll(c);
    }

    public boolean addAllNullSafe(int index, Collection<? extends T> c) {
        return AmountPredicates.hasIndex(c::size, index) && ExtendedListFunctions.addAllCondition(list, c) && list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return list.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        list.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        list.sort(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public DecoratedList<T> subList(int fromIndex, int toIndex) {
        return new DecoratedList<T>(list.subList(fromIndex, toIndex), type);
    }

    @Override
    public Spliterator<T> spliterator() {
        return list.spliterator();
    }

    @Override
    public Stream<T> stream() {
        return list.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return list.parallelStream();
    }

    @Override
    public boolean isNull() {
        return NullableFunctions.isNull(list);
    }

    @Override
    public boolean isNotNull() {
        return NullableFunctions.isNotNull(list);
    }

    @Override
    public boolean isEmpty() {
        return EmptiableFunctions.isEmpty(list);
    }

    @Override
    public boolean isNullOrEmpty() {
        return EmptiableFunctions.isNullOrEmpty(list);
    }

    @Override
    public boolean isNotNullAndNonEmpty() {
        return EmptiableFunctions.isNotNullAndNonEmpty(list);
    }

    @Override
    public boolean hasOnlyNonNullValues() {
        return EmptiableFunctions.hasOnlyNonNullValues(list);
    }

    @Override
    public T first() {
        return ExtendedListFunctions.first(list);
    }

    @Override
    public int lastIndex() {
        return ExtendedListFunctions.lastIndex(list);
    }

    @Override
    public T last() {
        return ExtendedListFunctions.last(list);
    }

    public <U> U tail(Class<U> clazz) {
        return clazz.cast(ExtendedListFunctions.tail(list));
    }


    public <U> U initials(Class<U> clazz) {
        return clazz.cast(ExtendedListFunctions.initials(list));
    }

    public <U> U subList(Class<U> clazz, int fromIndex, int toIndex) {
        return clazz.cast(list.subList(fromIndex, toIndex));
    }

    @Override
    public DecoratedList<T> tail() {
        return new DecoratedList<>(ExtendedListFunctions.tail(list), type);
    }

    @Override
    public DecoratedList<T> initials() {
        return new DecoratedList<>(ExtendedListFunctions.initials(list), type);
    }

    @Override
    public boolean isSingle() {
        return AmountPredicates.isSingle(list::size);
    }

    @Override
    public boolean isMany() {
        return AmountPredicates.isMany(list::size);
    }

    @Override
    public boolean hasMoreThan(int amount) {
        return AmountPredicates.hasMoreThan(list::size, amount);
    }

    @Override
    public boolean hasAtleast(int amount) {
        return AmountPredicates.hasAtleast(list::size, amount);
    }

    @Override
    public boolean hasIndex(int index) {
        return AmountPredicates.hasIndex(list::size, index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass()) || isNull()) {
            return false;
        }

        final var that = (DecoratedList<?>) o;
        return (
            CoreUtilities.isEqual(type, that.type) &&
            CoreUtilities.isEqual(list, that.list)
        );
    }

    public String getType() {
        return type;
    }

    public DecoratedList<T> get() {
        return this;
    }
}
