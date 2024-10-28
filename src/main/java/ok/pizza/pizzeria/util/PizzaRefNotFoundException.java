package ok.pizza.pizzeria.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PizzaRefNotFoundException extends RuntimeException {

	int id;
}
