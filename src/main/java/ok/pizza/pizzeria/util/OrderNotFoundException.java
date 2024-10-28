package ok.pizza.pizzeria.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderNotFoundException extends RuntimeException {

	int id;
}
