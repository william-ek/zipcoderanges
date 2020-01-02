package com.williamssonoma.zipcoderanges.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ZipCodeValidator.class)
@Documented
public @interface ZipCode {

  String message() default "{zipcode.invalid}";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
