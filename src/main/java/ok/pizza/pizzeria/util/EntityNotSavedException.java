package ok.pizza.pizzeria.util;

public class EntityNotSavedException extends RuntimeException {

	public EntityNotSavedException(String message) {
		super(message);
	}
}
