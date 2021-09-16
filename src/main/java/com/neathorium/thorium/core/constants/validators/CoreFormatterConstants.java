package com.neathorium.thorium.core.constants.validators;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.entry;

public abstract class CoreFormatterConstants {
    public static final String IS = "is";
    public static final String ISN_T = "isn't";

    public static final String ELEMENT = "Element ";
    public static final String EXTENSION = ".png";
    public static final String SCREENSHOT_NAME_START = "ss";
    public static final String SCREENSHOT_NAME_SEPARATOR = "-";
    public static final String EMPTY = "";
    public static final String OPTION_NOT = "not ";
    public static final String OPTION_EMPTY = "";
    public static final String NEW_LINE = "\n";
    public static final String END_LINE = "." + NEW_LINE;
    public static final String INVOCATION_EXCEPTION = "Exception occurred during invoke" + END_LINE;
    public static final String INVOCATION_SUCCESSFUL = "Invoke execution successful" + END_LINE;
    public static final String METHOD_PUT_IN_MAP = "Method put in map" + END_LINE;
    public static final String METHOD_ALREADY_IN_MAP = "Method was already in map" + END_LINE;
    public static final String SUCCESSFUL = " successful" + END_LINE;
    public static final String WAS_SUCCESSFUL = " was successful" + END_LINE;
    public static final String WASNT_SUCCESSFUL = " wasn't successful" + END_LINE;
    public static final String WAITING_WAS_SUCCESSFUL = "Waiting" + WAS_SUCCESSFUL;
    public static final String WAITING_WASNT_SUCCESSFUL = "Waiting" + WASNT_SUCCESSFUL;
    public static final String NON_EXCEPTION_MESSAGE = "No exception occurred" + END_LINE;
    public static final String PARAMETERS_WERE_WRONG = "Parameters were wrong" + END_LINE;
    public static final String DATA_NULL_OR_FALSE = "Data was null or false" + END_LINE;
    public static final String DEFAULT_ERROR_MESSAGE_STRING = "Returning default empty string" + END_LINE;
    public static final String EXECUTION_ENDED = "Execution ended" + END_LINE;
    public static final String WASNT_NULL = " wasn't null" + END_LINE;
    public static final String WERE_NULL = " were null" + END_LINE;
    public static final String WAS_NULL = " was null" + END_LINE;
    public static final String RESULT_WAS_NULL = "Result" + WAS_NULL;
    public static final String PASSED_DATA_WAS_NULL = "Passed data" + WAS_NULL;
    public static final String EXCEPTION_WAS_NULL = "Exception" + WAS_NULL;
    public static final String DEPENDENCY_WAS_NULL = "Dependency" + WAS_NULL;
    public static final String COULDNT_EXECUTE = "Couldn't execute";
    public static final String SUCCESSFULLY_EXECUTE = "Successfully executed";
    public static final String NULL_DATA = "nullData.";
    public static final String EXECUTION_STATUS = "Execution status";
    public static final String PARAMETER_ISSUES = "There were parameter issue(s)";
    public static final String PARAMETER_ISSUES_END = PARAMETER_ISSUES + END_LINE;
    public static final String COLON = ":";
    public static final String COLON_SPACE = COLON + " ";
    public static final String EXECUTION_STATUS_COLON_SPACE = EXECUTION_STATUS + COLON_SPACE;
    public static final String COLON_NEWLINE = COLON + NEW_LINE;
    public static final String PARAMETER_ISSUES_LINE = PARAMETER_ISSUES + COLON_NEWLINE;
    public static final String VOID_CLASS_GENERIC = Void.class.toGenericString();
    public static final String WAITING_FAILED = "Waiting failed" + COLON_NEWLINE;
    public static final String WAITING_SUCCESSFUL = "Waiting" + WAS_SUCCESSFUL + NEW_LINE;

    public static final String VALUE_DOES = "Value does ";
    public static final String VALUE_DOESNT = VALUE_DOES + "not ";
    public static final String WITH_PAD_VALUE = "with pad value";
    public static final String START = "start ";
    public static final String END = "end ";

    public static final String ACTUAL_LIST_TYPE = "Actual Type of the list";
    public static final String EXPECTED_LIST_TYPE =  "Expected List type";

    public static final Map<String, String> isMessageMap = Collections.unmodifiableMap(
        new LinkedHashMap<>(
            Map.ofEntries(
                entry("true", IS),
                entry("false", ISN_T),
                entry("nottrue", ISN_T),
                entry("notfalse", IS)
            )
        )
    );
}
