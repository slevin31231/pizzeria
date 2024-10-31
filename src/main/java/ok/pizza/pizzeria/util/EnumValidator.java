package ok.pizza.pizzeria.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, CharSequence> {

	private List<String> acceptedValues;
	private String message;

	@Override
	public void initialize(ValidEnum annotation) {
		this.acceptedValues = Stream.of(annotation.enumClass()
									.getEnumConstants())
									.map(Enum::name)
									.toList();

		this.message = 	!annotation.message().equals("_")
						? annotation.message()
						: "Тип має бути: " + String.join(", ", acceptedValues);
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null)
			return true;

		boolean isValid = acceptedValues.contains(value.toString());

		if (!isValid) {
			constraintValidatorContext.disableDefaultConstraintViolation();
			constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		}
		return isValid;
	}
}
