package com.neathorium.thorium.core.platform;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.platform.enums.PlatformKey;
import com.neathorium.thorium.core.platform.namespaces.PlatformFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;
import java.util.stream.Stream;

public class PlatformFunctionsTests {

    public static Stream<Arguments> getPlatformMapWithUnknownProvider() {
        final var baseMessage = "getPlatformMap: Parameters were ";
        return Stream.of(
            Arguments.of("The specified entries show up correctly in the result map" + CoreFormatterConstants.END_LINE, "WindowsEntry", "MacEntry", "LinuxEntry", "UnknownEntry", 4)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getPlatformMapWithUnknownProvider")
    public void isLessThanExpectedTest(String name, String windowsEntry, String macEntry, String linuxEntry, String unknownEntry, int size) {
        final var result = PlatformFunctions.getPlatformMap(windowsEntry, macEntry, linuxEntry);
        Assertions.assertTrue(
            (
                Objects.equals(result.size(), size - 1) &&
                Objects.equals(result.get(PlatformKey.WINDOWS), windowsEntry) &&
                Objects.equals(result.get(PlatformKey.LINUX), linuxEntry) &&
                Objects.equals(result.get(PlatformKey.MAC), macEntry) &&
                Objects.equals(result.getOrDefault(PlatformKey.UNKNOWN, ""), "")
            ),
            "Map size or entries didn't match"
        );
    }
}
