package ink.labrador.mmsmanager.integration.constraints;

import ink.labrador.mmsmanager.integration.validator.IsStringInValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsStringInValidator.class)
public @interface IsStringIn {
    String[] value();
    String message() default "Parameter is invalid";
    boolean caseSensitive() default true;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
