package ok.pizza.pizzeria.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PizzaRefDto {

	@NotNull(message = "Значення має бути заповненим")
	@Size(min = 1, max = 7, message = "Кількість інгредієнтів повинна бути від 1 до 7")
	private List<String> ingredientsNames;

	@Positive(message = "Значення має бути більше 0")
	private int priceForSmall;

	@Positive(message = "Значення має бути більше 0")
	private int priceForBig;

	@Positive(message = "Значення має бути більше 0")
	private int weightForSmall;

	@Positive(message = "Значення має бути більше 0")
	private int weightForBig;
}
