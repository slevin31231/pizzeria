package ok.pizza.pizzeria.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

	private String message;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime dateTime;

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
