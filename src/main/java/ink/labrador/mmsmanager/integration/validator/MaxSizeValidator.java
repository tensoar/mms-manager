package ink.labrador.mmsmanager.integration.validator;

import ink.labrador.mmsmanager.integration.constraints.MaxSize;
import ink.labrador.mmsmanager.integration.exception.ValidatorException;
import ink.labrador.mmsmanager.util.ArrayUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class MaxSizeValidator implements ConstraintValidator<MaxSize, Object> {
    long max;

    @Override
    public void initialize(MaxSize constraintAnnotation) {
        max = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).length() <= max;
        }
        if (value instanceof Collection<?>) {
            return ((Collection<?>) value).size() <= max;
        }
        if (ArrayUtil.isArray(value)) {
            return ArrayUtil.length(value) <= max;
        }
        throw new ValidatorException("参数类型错误,必须是列表、数组或字符串");
    }
}
