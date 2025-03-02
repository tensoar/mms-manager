package ink.labrador.mmsmanager.integration.validator;


import ink.labrador.mmsmanager.integration.constraints.IsInStaticConstant;
import ink.labrador.mmsmanager.integration.exception.ValidatorException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class IsInStaticConstantValidator implements ConstraintValidator<IsInStaticConstant, Object>, IIsInValidator {
    private Set<Object> opts;
    private boolean caseSensitive;

    @Override
    public void initialize(IsInStaticConstant isInConstant) {
        Class<?> clazz = isInConstant.value();
        this.caseSensitive = isInConstant.caseSensitive();
        this.opts = new HashSet<>();
        for (Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    opts.add(field.get(clazz));
                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
                    throw new ValidatorException("操作失败,请检查字段名称正确性");
                }
            }
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return  true;
        }
        return isValid(value, opts, this.caseSensitive);
    }
}