package com.neathorium.thorium.core.constants.formatter;

import com.neathorium.thorium.core.namespaces.factories.NumberConditionDataFactory;
import com.neathorium.thorium.core.records.formatter.NumberConditionData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.SizablePredicates;

public abstract class NumberConditionDataConstants {
    public static final NumberConditionData EQUAL_TO = NumberConditionDataFactory.getWithDefaultParameterName("isEqualToExpected", "equal to", SizablePredicates::isSizeEqualTo);
    public static final NumberConditionData LESS_THAN = NumberConditionDataFactory.getWithDefaultParameterName("isLessThanExpected", "less than", BasicPredicates::isSmallerThan);
    public static final NumberConditionData MORE_THAN = NumberConditionDataFactory.getWithDefaultParameterName("isMoreThanExpected", "more than", BasicPredicates::isBiggerThan);
}
