package ok.pizza.pizzeria.util;

import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IngredientValidator implements Validator {

	private final IngredientRepository ingredientRepository;

	@Autowired
	public IngredientValidator(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Ingredient.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ingredient ingredient = (Ingredient) target;
		if (ingredientRepository.findIngredientByNameAndType(ingredient.getName(), ingredient.getType()).isPresent()) {
			errors.reject("duplicateIngredient","Цей інгредієнт вже існує");
		}
	}
}
