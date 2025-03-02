package ink.labrador.mmsmanager.integration.validator;

import ink.labrador.mmsmanager.integration.constraints.IsIntegerIn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class IsIntegerInValidator implements ConstraintValidator<IsIntegerIn, Object>, IIsInValidator {
    private Set<Integer> options;

    @Override
    public void initialize(IsIntegerIn constraintAnnotation) {
        this.options = Arrays.stream(constraintAnnotation.value()).boxed().collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return isValid(value, options);
    }
}
