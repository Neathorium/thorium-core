package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.namespaces.validators.CoreFormatter;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.EqualsPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public interface MapFunctions {
    static <KeyType, ValueType> Data<Map<KeyType, ValueType>> putAllAbsent(Map<KeyType, ValueType> target, Map<KeyType, ValueType> source) {
        final var originalTargetSize = target.size();
        final var returnMap = new HashMap<KeyType, ValueType>();
        var exception = ExceptionConstants.EXCEPTION;
        try {
            KeyType key = null;
            final var entries = source.entrySet();
            for (var entry : entries) {
                key = entry.getKey();
                (target.containsKey(key) ? returnMap : target).put(key, entry.getValue());
            }
        } catch (RuntimeException ex) {
            exception = ex;
        }

        final var status = ExceptionFunctions.isNonException(exception);
        final var difference = target.size() - originalTargetSize;
        final var message = (BasicPredicates.isNonZero(difference) ? "New entries(\"" + difference + "\") have been added" : "No new entries added") + CoreFormatterConstants.END_LINE;
        return DataFactoryFunctions.getWith(returnMap, status, "putAllAbsent", message);
    }

    static <KeyType> Data<Boolean> absentKey(Map<KeyType, ?> map, KeyType key) {
        final var nameof = "absentKey";
        final var errors = (
            CoreFormatter.isNullMessageWithName(map, "Map") +
            CoreFormatter.isNullMessageWithName(key, "Key")
        );
        if (StringUtils.isNotBlank(errors)) {
            return DataFactoryFunctions.getInvalidBooleanWith(nameof, errors);
        }

        final var status = BooleanUtilities.isFalse(map.containsKey(key));
        final var message = "Key was" + (status ? "m't" : "") + " found in map" + CoreFormatterConstants.END_LINE;
        return DataFactoryFunctions.getWith(status, status, nameof, message);
    }

    static <KeyType, ValueType> Data<Boolean> putIfAbsentInvalidOrFalse(
        Map<KeyType, Data<? extends ValueType>> map,
        Data<? extends ValueType> data,
        KeyType key
    ) {
        final var nameof = "putIfMissingInvalidOrFalse";
        final var errors = (
            CoreFormatter.isNullMessageWithName(map, "Map") +
            CoreFormatter.isNullMessageWithName(data, "Data") +
            CoreFormatter.isNullMessageWithName(key, "Map Key")
        );
        if (StringUtils.isNotBlank(errors)) {
            return DataFactoryFunctions.getInvalidBooleanWith(nameof, errors);
        }

        if (map.containsKey(key)) {
            final var object = map.get(key);
            if (DataPredicates.isInvalidOrFalse(object)) {
                map.replace(key, data);
            } else {
                final var status = EqualsPredicates.isEqual(object, data);
                final var message = status ? "Same data value already stored in map with passed key." : (
                    "Different valid data stored in map with passed key" + CoreFormatterConstants.END_LINE +
                    "Already stored data: " + object.toString() +
                    "Passed data: " + data.toString()
                );
                return DataFactoryFunctions.getWith(status, true, nameof, message);
            }
        } else {
            map.put(key, data);
        }

        final var object = map.getOrDefault(key, null);
        final var status = NullablePredicates.isNotNull(object) && EqualsPredicates.isEqual(object, data);
        final var message = "There were" + (status ? "n't any" : "") + " issues" + CoreFormatterConstants.END_LINE;
        return DataFactoryFunctions.getBoolean(status, nameof, message);

    }
}
