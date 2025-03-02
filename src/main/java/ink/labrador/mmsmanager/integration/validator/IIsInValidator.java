package ink.labrador.mmsmanager.integration.validator;


import ink.labrador.mmsmanager.support.TypeCheck;

import java.util.Set;

public interface IIsInValidator {

    default <T> boolean isValid(Object value,  Set<T> options, boolean caseSensitive) {
        if (value == null) {
            return true;
        }
        if (TypeCheck.isIterable(value)) {
            for (Object o: (Iterable<?>) value) {
                if (o == null) {
                    return false;
                }
                if (!isValueValid(o, options, caseSensitive)) {
                    return false;
                }
            }
            return true;
        } else if (TypeCheck.isArray(value)) {
            for (Object o: (Object[]) value) {
                if (o == null) {
                    return false;
                }
                if (!isValueValid(o, options, caseSensitive)) {
                    return false;
                }
            }
            return true;
        } else {
            return isValueValid(value, options, caseSensitive);
        }
    }

    default <T> boolean isValid(Object value,  Set<T> options) {
        return isValid(value, options, false);
    }


    default <T> boolean isValueValid(Object value, Set<T> options, boolean isCaseSensitive) {
        if (value == null) {
            return false;
        }
        Object sensitiveValue = value;
        if (value instanceof String && !isCaseSensitive) {
            sensitiveValue = ((String) value).toLowerCase();
        }
        for (Object option: options) {
            Object sensitiveOption = option instanceof String && !isCaseSensitive ? ((String) option).toLowerCase() : option;
            if (sensitiveOption.equals(sensitiveValue)) {
                return true;
            }
        }
        return false;
    }
}
