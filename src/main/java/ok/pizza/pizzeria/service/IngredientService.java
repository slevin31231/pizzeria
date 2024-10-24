package ok.pizza.pizzeria.service;

import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

	private final IngredientRepository ingredientRepository;

	@Autowired
	public IngredientService(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	public List<Ingredient> getAllIngredients() {
		return ingredientRepository.findAll();
	}
}
