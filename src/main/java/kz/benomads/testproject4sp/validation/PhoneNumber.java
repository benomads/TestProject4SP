package kz.benomads.testproject4sp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "Invalid phone number. The correct format is +7### ### ####";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
