package ok.pizza.pizzeria.util;

import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.entity.PizzaRef;
import ok.pizza.pizzeria.repository.PizzaRefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PizzaRefValidator implements Validator {

	private final PizzaRefRepository pizzaRefRepository;

	@Autowired
	public PizzaRefValidator(PizzaRefRepository pizzaRefRepository) {
		this.pizzaRefRepository = pizzaRefRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return PizzaRef.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PizzaRef valPizzaRef = (PizzaRef) target;
		List<Ingredient> valIngredients = valPizzaRef.getIngredients();
		Set<Ingredient> valIngredientSet = new HashSet<>(valIngredients);

		List<PizzaRef> pizzaRefList = pizzaRefRepository.findAll();
		for (PizzaRef pizzaRef : pizzaRefList) {
			Set<Ingredient> ingredientSet = new HashSet<>(pizzaRef.getIngredients());
			if (valIngredientSet.equals(ingredientSet)) {
				if (valPizzaRef.getId() != pizzaRef.getId()) {
					errors.reject("duplicateIngredientSet","Піца з таким набором інгредієнтів вже існує");
				}
			}
		}
	}
}