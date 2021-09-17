package com.neathorium.thorium.core.extensions.namespaces.predicates;

import com.neathorium.thorium.core.constants.DoubleConstants;

public interface DoublePredicates {
    static boolean isBiggerThan(double value, double limit) {
        return Double.compare(value, limit) == 1;
    }

    static boolean isSmallerThan(double value, double limit) {
        return Double.compare(value, limit) == -1;
    }

    static boolean isZero(double value, double threshold) {
        return Math.abs(value) < threshold;
    }

    static boolean isZero(double value) {
        return isZero(value, DoubleConstants.THRESHOLD);
    }

    static boolean isNonNegative(double value) {
        return isBiggerThan(value, DoubleConstants.NEGATIVE_THRESHOLD);
    }

    static boolean isNegative(double value) {
        return isBiggerThan(0.0, value);
    }

    static boolean isPositiveNonZero(double value) {
        return isBiggerThan(value, 0.0);
    }

    static boolean isNonZero(double value) {
        return !isZero(value);
    }

    static boolean isZeroOrNonPositive(double value) {
        return isSmallerThan(value, DoubleConstants.THRESHOLD);
    }
}
