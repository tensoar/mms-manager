package ink.labrador.mmsmanager.integration.validator;

import ink.labrador.mmsmanager.integration.constraints.MinSize;
import ink.labrador.mmsmanager.integration.exception.ValidatorException;
import ink.labrador.mmsmanager.util.ArrayUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class MinSizeValidator implements ConstraintValidator<MinSize, Object> {
    long min;

    @Override
    public void initialize(MinSize constraintAnnotation) {
        min = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).length() >= min;
        }
        if (value instanceof Collection<?>) {
            return ((Collection<?>) value).size() >= min;
        }
        if (ArrayUtil.isArray(value)) {
            return ArrayUtil.length(value) >= min;
        }
        throw new ValidatorException("参数类型错误,必须是列表、数组或字符串");
    }
}
