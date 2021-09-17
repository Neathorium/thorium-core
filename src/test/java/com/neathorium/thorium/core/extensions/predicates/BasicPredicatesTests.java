package com.neathorium.thorium.core.extensions.predicates;

import com.neathorium.thorium.core.extensions.namespaces.predicates.BasicPredicates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class BasicPredicatesTests {
    public static Stream<Arguments> isBiggerThanProvider() {
        return Stream.of(
            Arguments.of("1 bigger than 0", 1, 0, true, "1 was not bigger than 0"),
            Arguments.of("-1 bigger than Integer.MIN_VALUE", -1, Integer.MIN_VALUE, true, "-1 was not bigger than Integer.MIN_VALUE"),
            Arguments.of("Integer.MAX_VALUE bigger than 0", Integer.MAX_VALUE, 0, true, "Integer.MAX_VALUE was not bigger than Integer.MIN_VALUE"),
            Arguments.of("Integer.MAX_VALUE bigger than Integer.MIN_VALUE", Integer.MAX_VALUE, Integer.MIN_VALUE, true, "Integer.MAX_VALUE was not bigger than Integer.MIN_VALUE"),
            Arguments.of("1 bigger than -1", 1, -1, true, "1 was not bigger than -1"),
            Arguments.of("0 not bigger than Integer.MAX_VALUE", 0, Integer.MAX_VALUE, false, "0 was bigger than Integer.MAX_VALUE"),
            Arguments.of("0 not bigger than 0", 0, 0, false, "0 was bigger than 0"),
            Arguments.of("1 not bigger than 1", 1, 1, false, "1 was bigger than 1"),
            Arguments.of("-1 not bigger than -1", -1, -1, false, "-1 was bigger than -1"),
            Arguments.of("-1 not bigger than 1", -1, 1, false, "-1 was bigger than 1")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("isBiggerThanProvider")
    @Tag("isBiggerThan")
    public void isBiggerThanTest(String name, int number, int limit, boolean expectedStatus, String errorMessage) {
        final var result = BasicPredicates.isBiggerThan(number, limit);
        Assertions.assertEquals(result, expectedStatus, errorMessage);
    }
}
