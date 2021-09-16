package com.neathorium.thorium.core.extensions.namespaces.factories;

import com.neathorium.thorium.core.extensions.DecoratedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface DecoratedListFactory {
    static <T> DecoratedList<T> getWith(List<T> list, Class<?> clazz) {
        return new DecoratedList<>(list, clazz.getTypeName());
    }

    static <T> DecoratedList<T> getWithObjectClass(List<T> list) {
        return getWith(list, Object.class);
    }

    static DecoratedList<String> getWith(List<String> list) {
        return getWith(list, String.class);
    }

    static DecoratedList<String> getWith(Collection<String> collection) {
        return getWith(collection, String.class);
    }

    static <T> DecoratedList<T> getWith(T[] list, Class<?> type) {
        return getWith(Arrays.asList(list), type);
    }

    static <T> DecoratedList<T> getWith(Collection<T> collection, Class<?> type) {
        return getWith(new ArrayList<>(collection), type);
    }

    static <T> DecoratedList<T> getWith(T element, Class<?> type) {
        return getWith(Collections.singletonList(element), type);
    }

    static <T> DecoratedList<T> getWith(Class<T> type) {
        return getWith(new ArrayList<>(), type);
    }

    static <T> DecoratedList<T> getWith(T[] list) {
        return getWithObjectClass(Arrays.asList(list));
    }

    static <T> DecoratedList<T> getWithDefaults() {
        return getWithObjectClass(new ArrayList<>());
    }

    static <T> DecoratedList<T> getWith(T element) {
        return getWithObjectClass(List.of(element));
    }
}
