package com.neathorium.thorium.core.clipboard;

import com.neathorium.thorium.core.data.namespaces.DataFunctions;
import com.neathorium.thorium.core.namespaces.clipboard.ClipboardFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClipboardTests {
    @DisplayName("Copy to clipboard")
    @Test
    void oneFailsSecond() {
        final var result = ClipboardFunctions.copyToClipboard("Test String");
        Assertions.assertTrue(result.STATUS(), DataFunctions.getFormattedMessage(result));
    }
}
