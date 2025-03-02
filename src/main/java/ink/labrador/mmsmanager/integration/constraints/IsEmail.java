package ink.labrador.mmsmanager.integration.constraints;


import jakarta.validation.Payload;

public @interface IsEmail {
    boolean allowDisplayName() default false;
    boolean ignoreMaxLength() default false;
    String message() default "Parameter is invalid";
    boolean caseSensitive() default true;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
