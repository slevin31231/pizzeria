package ok.pizza.pizzeria.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class PizzaRefDTO {

	@NotNull(message = "Значення має бути заповненим")
	@Size(min = 1, max = 7, message = "Кількість інгредієнтів повинна бути від 1 до 7")
	private Set<String> namesOfIngredients;

	@Positive(message = "Значення має бути більше 0")
	private int priceForSmall;

	@Positive(message = "Значення має бути більше 0")
	private int priceForBig;

	@Positive(message = "Значення має бути більше 0")
	private int weightForSmall;

	@Positive(message = "Значення має бути більше 0")
	private int weightForBig;

	public List<String> getNamesOfIngredients() {
		return new ArrayList<>(namesOfIngredients);
	}
}
