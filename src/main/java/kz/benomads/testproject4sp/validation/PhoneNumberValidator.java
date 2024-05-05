package kz.benomads.testproject4sp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final String PHONE_NUMBER_PATTERN = "^\\+7\\d{3} \\d{3} \\d{4}$";

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return phoneNumber != null && phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }
}