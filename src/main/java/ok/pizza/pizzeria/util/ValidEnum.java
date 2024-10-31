package ok.pizza.pizzeria.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {

	Class<? extends Enum<?>> enumClass();
	String message() default "_";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
