package ok.pizza.pizzeria.util;

import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Data
public class ErrorResponse {

	private long timestamp;

	private String message;

	public static void makeErrorResponse(BindingResult bindingResult) {
		StringBuilder stringBuilder = new StringBuilder();
		if (bindingResult.hasFieldErrors()) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				stringBuilder.append(fieldError.getField())
						.append(" - ")
						.append(fieldError.getDefaultMessage())
						.append("! ");
			}
		}
		if (bindingResult.hasGlobalErrors()) {
			for (ObjectError objectError : bindingResult.getGlobalErrors()) {
				stringBuilder.append(objectError.getCode())
						.append(" - ")
						.append(objectError.getDefaultMessage())
						.append("! ");
			}
		}
		throw new EntityNotSavedException(stringBuilder.toString().trim());
	}
}
