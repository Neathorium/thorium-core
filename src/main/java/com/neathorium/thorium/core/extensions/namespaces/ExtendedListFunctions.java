package com.neathorium.thorium.core.extensions.namespaces;

import com.neathorium.thorium.core.extensions.namespaces.predicates.AmountPredicates;
import com.neathorium.thorium.core.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.core.extensions.records.ExtensionListData;
import com.neathorium.thorium.core.extensions.constants.IExtendedListConstants;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface ExtendedListFunctions {
    private static <T> List<T> conditionalSublist(ExtensionListData<T> data, Predicate<Supplier<Integer>> condition, List<T> list) {
        return condition.test(list::size) ? list.subList(data.START_INDEX, data.END_INDEX_FUNCTION.apply(list)) : List.of(data.GETTER.apply(list));
    }

    static <T> boolean addAllCondition(List<T> list, Collection<? extends T> c) {
        return NullableFunctions.isNotNull(list) && EmptiableFunctions.hasOnlyNonNullValues(c);
    }

    static <T> T first(List<T> list) {
        return list.get(IExtendedListConstants.FIRST_INDEX);
    }

    static <T> int lastIndex(List<T> list) {
        final var size = SizableFunctions.size(list::size);
        return BasicPredicates.isPositiveNonZero(size) ? size - 1 : IExtendedListConstants.NOT_IN_LIST_INDEX;
    }

    static <T> int secondLastIndex(List<T> list) {
        return lastIndex(list) - 1;
    }

    static <T> T last(List<T> list) {
        return list.get(lastIndex(list));
    }

    static <T> List<T> tail(List<T> list) {
        final var listData = new ExtensionListData<T>(ExtendedListFunctions::last, ExtendedListFunctions::lastIndex, IExtendedListConstants.SECOND_INDEX);
        return conditionalSublist(listData, CardinalitiesFunctions.invertBoolean(AmountPredicates::isDouble), list);
    }

    static <T> List<T> initials(List<T> list) {
        final var listData = new ExtensionListData<T>(ExtendedListFunctions::first, ExtendedListFunctions::secondLastIndex, IExtendedListConstants.FIRST_INDEX);
        return conditionalSublist(listData, AmountPredicates::isAtleastDouble, list);
    }
}
