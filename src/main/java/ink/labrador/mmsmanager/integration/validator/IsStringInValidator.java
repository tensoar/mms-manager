package ink.labrador.mmsmanager.integration.validator;

import ink.labrador.mmsmanager.integration.constraints.IsStringIn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class IsStringInValidator implements ConstraintValidator<IsStringIn, String>, IIsInValidator {
    private Set<String> options;
    private boolean caseSensitive;

    @Override
    public void initialize(IsStringIn constraintAnnotation) {
        String[] values = constraintAnnotation.value();
        this.caseSensitive = constraintAnnotation.caseSensitive();
        this.options = Arrays.stream(values)
                .map(v -> caseSensitive ? v : v.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return isValid(value, options, this.caseSensitive);
    }
}
