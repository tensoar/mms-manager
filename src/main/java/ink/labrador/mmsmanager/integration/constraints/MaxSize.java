package ink.labrador.mmsmanager.integration.constraints;

import ink.labrador.mmsmanager.integration.validator.MaxSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxSizeValidator.class)
public @interface MaxSize {
    long value();
    String message() default "Parameter is invalid";
    boolean caseSensitive() default true;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
