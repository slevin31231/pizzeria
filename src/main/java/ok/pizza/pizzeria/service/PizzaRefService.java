package ok.pizza.pizzeria.service;

import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.entity.Pizza;
import ok.pizza.pizzeria.entity.PizzaRef;
import ok.pizza.pizzeria.repository.PizzaRefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PizzaRefService {

	private final PizzaRefRepository pizzaRefRepository;

	@Autowired
	public PizzaRefService(PizzaRefRepository pizzaRefRepository) {
		this.pizzaRefRepository = pizzaRefRepository;
	}

	public List<PizzaRef> getPizzaRefList() {
		return pizzaRefRepository.findAll();
	}

	public Pizza findByIdAndCopy(int pizzaId, boolean big) {
		Optional<PizzaRef> selectedPizzaRef = pizzaRefRepository.findById(pizzaId);
		return selectedPizzaRef.get().createPizza(big);
	}

	public Pizza getOrCreateAndCopy(PizzaRef newPizzaRef, boolean big) {
		List<Ingredient> newIngredients = newPizzaRef.getIngredients();
		Set<Ingredient> newIngredientSet = new HashSet<>(newIngredients);

		List<PizzaRef> pizzaRefList = pizzaRefRepository.findAll();
		for (PizzaRef pizzaRef : pizzaRefList) {
			Set<Ingredient> ingredientSet = new HashSet<>(pizzaRef.getIngredients());
			if (newIngredientSet.equals(ingredientSet)) {
				return pizzaRef.createPizza(big);
			}
		}
		newIngredients.forEach(ingredient -> ingredient.getPizzaRefList().add(newPizzaRef));
		newPizzaRef.setPriceAndWeight();
		pizzaRefRepository.save(newPizzaRef);
		return newPizzaRef.createPizza(big);
	}
}
