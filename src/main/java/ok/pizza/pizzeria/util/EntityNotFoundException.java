package ok.pizza.pizzeria.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException {

	private int id;
}
