package com.neathorium.thorium.core.namespaces.validators;

import com.neathorium.thorium.core.records.MethodParametersData;
import com.neathorium.thorium.core.records.MethodSourceData;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface MethodRepositoryValidators {
    static String validateMethodSourceData(MethodSourceData data) {
        var message = CoreFormatter.isNullMessageWithName(data, "Method Get Parameters data");
        if (isBlank(message)) {
            message +=  (
                CoreFormatter.isNullMessageWithName(data.methodMap, "Method map") +
                CoreFormatter.isNullMessageWithName(data.list, "Method List") +
                CoreFormatter.isFalseMessageWithName(data.defaultValue, "Default Value")
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("validateMethodGetCommonParametersData", message);
    }

    static String validateMethodParametersData(MethodParametersData parameterData) {
        var message = CoreFormatter.isNullMessageWithName(parameterData, "Method parameters data");
        if (isBlank(message)) {
            message +=  (
                CoreFormatter.isNullMessageWithName(parameterData.validator, "Condition method") +
                CoreFormatter.isBlankMessageWithName(parameterData.methodName, "Method name")
            );
        }

        return CoreFormatter.getNamedErrorMessageOrEmpty("validateMethodParametersData", message);
    }


    static String validateGetMethodFromList(MethodSourceData data, MethodParametersData parameterData) {
        return validateMethodSourceData(data) + validateMethodParametersData(parameterData);
    }
}
