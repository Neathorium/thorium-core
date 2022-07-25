package com.neathorium.thorium.core.namespaces;

import com.neathorium.thorium.core.constants.validators.CoreFormatterConstants;
import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.namespaces.validators.HandlerResultDataValidator;
import com.neathorium.thorium.core.records.HandleResultData;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface ExceptionHandlers {
    static <CastParameterType, ReturnType> Data<ReturnType> classCastHandler(HandleResultData<CastParameterType, ReturnType> data) {
        final var nameof = "classCastHandler";
        final var errorMessage = HandlerResultDataValidator.isInvalidHandlerResultDataMessage(data);
        if (isNotBlank(errorMessage)) {
            return DataFactoryFunctions.getInvalidWith(null, nameof, errorMessage);
        }

        var exception = ExceptionConstants.EXCEPTION;
        var result = data.DEFAULT_VALUE();
        try {
            result = data.CASTER().apply(data.PARAMETER());
        } catch (ClassCastException ex) {
            exception = ex;
        }

        final var status = ExceptionFunctions.isNonException(exception);
        return DataFactoryFunctions.getWith(result, status, status ? CoreFormatterConstants.INVOCATION_SUCCESSFUL : CoreFormatterConstants.INVOCATION_EXCEPTION, exception);
    }
}
