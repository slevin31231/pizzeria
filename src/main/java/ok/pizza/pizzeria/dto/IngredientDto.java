package ok.pizza.pizzeria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.util.ValidEnum;

@Data
public class IngredientDto {

	@NotBlank(message = "Ім'я має бути заповненим")
	private String name;

	@NotNull(message = "Тип має бути заповненим")
	@ValidEnum(enumClass = Ingredient.Type.class)
	private String type;
}
