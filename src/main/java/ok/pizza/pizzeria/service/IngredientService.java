package ok.pizza.pizzeria.service;

import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.entity.PizzaRef;
import ok.pizza.pizzeria.repository.IngredientRepository;
import ok.pizza.pizzeria.util.EmptyEntityListException;
import ok.pizza.pizzeria.util.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

	private final IngredientRepository ingredientRepository;

	@Autowired
	public IngredientService(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	public List<Ingredient> getIngredients() {
		return ingredientRepository.findAll();
	}

	public Ingredient getIngredient(int id) {
		Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
		return optionalIngredient.orElseThrow(() -> new EntityNotFoundException(id));
	}

	public List<Ingredient> getIngredientsByNames(List<String> names) {
		List<Ingredient> ingredients = ingredientRepository.findAllIngredientsByNameIn(names);

		if (ingredients.isEmpty())
			throw new EmptyEntityListException();

		if (names.size() != ingredients.size())
			throw new EntityNotFoundException();
		return ingredients;
	}

	public void deleteIngredient(int id) {
		Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
		Ingredient ingredient = optionalIngredient.orElseThrow(() -> new EntityNotFoundException(id));
		List<PizzaRef> pizzaRefList = ingredient.getPizzaRefList();
		for (PizzaRef pizzaRef : pizzaRefList) {
			pizzaRef.getIngredients().remove(ingredient);
		}
		ingredientRepository.delete(ingredient);
	}

	public Ingredient saveIngredient(Ingredient ingredient) {
		return ingredientRepository.save(ingredient);
	}
}
