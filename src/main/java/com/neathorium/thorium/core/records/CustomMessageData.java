package com.neathorium.thorium.core.records;

import com.neathorium.thorium.core.extensions.interfaces.functional.TriFunction;
import com.neathorium.thorium.core.extensions.interfaces.functional.boilers.IGetMessage;
import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;

import java.util.Objects;
import java.util.function.Function;

public class CustomMessageData implements IGetMessage {
    public final String prefix;
    public final String suffix;
    public final TriFunction<String, Boolean, String, String> messageFormatter;

    public CustomMessageData(TriFunction<String, Boolean, String, String> messageFormatter, String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.messageFormatter = messageFormatter;
    }

    public CustomMessageData(String prefix, String suffix) {
        this(CoreFormatter::formatPrefixSuffixMessage, prefix, suffix);
    }

    public CustomMessageData() {
        this.prefix = null;
        this.suffix = null;
        this.messageFormatter = null;
    }

    @Override
    public Function<Boolean, String> get() {
        return status -> Objects.isNull(messageFormatter) ? "MessageFormatter was null" + CoreFormatterConstants.END_LINE : messageFormatter.apply(prefix, status, suffix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (CustomMessageData) o;
        return (
            Objects.equals(prefix, that.prefix) &&
            Objects.equals(suffix, that.suffix) &&
            Objects.equals(messageFormatter, that.messageFormatter)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, suffix, messageFormatter);
    }
}
