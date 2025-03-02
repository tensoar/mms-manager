package ink.labrador.mmsmanager.integration.validator;

import ink.labrador.mmsmanager.integration.constraints.IsLongIn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class IsLongInValidator implements ConstraintValidator<IsLongIn, Long>, IIsInValidator {
    private Set<Long> options;

    @Override
    public void initialize(IsLongIn constraintAnnotation) {
        this.options = Arrays.stream(constraintAnnotation.value()).boxed().collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return isValid(value, options);
    }
}
