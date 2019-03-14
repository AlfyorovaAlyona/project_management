package com.projectmanagement.common.utils;

public class ValidationUtils {
    public static void validateIsNotNull(Object object,
                                         String exceptionMessage) throws ValidationException {
        if (object == null) {
            throw new ValidationException(exceptionMessage);
        }
    }
}
