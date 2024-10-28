package ok.pizza.pizzeria.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IngredientNotFoundException extends RuntimeException {

	int id;
}