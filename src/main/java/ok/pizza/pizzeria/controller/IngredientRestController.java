package ok.pizza.pizzeria.controller;

import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.service.IngredientService;
import ok.pizza.pizzeria.util.ErrorResponse;
import ok.pizza.pizzeria.util.IngredientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientRestController {

	private final IngredientService ingredientService;

	@Autowired
	public IngredientRestController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	@GetMapping
	public List<Ingredient> getIngredients() {
		return ingredientService.getIngredients();
	}

	@GetMapping("/{id}")
	public Ingredient getIngredient(@PathVariable("id") int id) {
		return ingredientService.getIngredient(id);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteIngredient(@PathVariable("id") int id) {
		ingredientService.deleteIngredient(id);
		return ResponseEntity.ok("Ingredient with id-%d successfully deleted!".formatted(id));
	}

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleException(IngredientNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Ingredient with id-%d wasn't found!".formatted(exception.getId()));
		errorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
