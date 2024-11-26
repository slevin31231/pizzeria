package ok.pizza.pizzeria.controller;

import jakarta.validation.Valid;
import ok.pizza.pizzeria.dto.PizzaRefDTO;
import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.entity.PizzaRef;
import ok.pizza.pizzeria.service.IngredientService;
import ok.pizza.pizzeria.service.PizzaRefService;
import ok.pizza.pizzeria.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaRefRestController {

	private final PizzaRefService pizzaRefService;
	private final IngredientService ingredientService;
	private final PizzaRefValidator pizzaRefValidator;

	@Autowired
	public PizzaRefRestController(PizzaRefService pizzaRefService,
								  IngredientService ingredientService,
								  PizzaRefValidator pizzaRefValidator) {
		this.pizzaRefService = pizzaRefService;
		this.ingredientService = ingredientService;
		this.pizzaRefValidator = pizzaRefValidator;
	}

	@GetMapping
	public List<PizzaRef> getPizzaRefList() {
		return pizzaRefService.getPizzaRefList();
	}

	@GetMapping("/{id}")
	public PizzaRef getPizzaRef(@PathVariable("id") int id) {
		return pizzaRefService.getPizzaRef(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePizzaRef(@PathVariable("id") int id) {
		pizzaRefService.deletePizzaRef(id);
		return ResponseEntity.ok("Pizza with id-%d successfully deleted!".formatted(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PizzaRef postPizzaRed(@RequestBody @Valid PizzaRefDTO newPizzaRefDTO,
								 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ErrorResponse.makeErrorResponse(bindingResult);
		}
		PizzaRef newPizzaRef = new PizzaRef();
		List<Ingredient> ingredients = ingredientService.getIngredientsByNames(newPizzaRefDTO.getNamesOfIngredients());

		newPizzaRef.setIngredients(ingredients);
		newPizzaRef.setPriceForSmall(newPizzaRefDTO.getPriceForSmall());
		newPizzaRef.setPriceForBig(newPizzaRefDTO.getPriceForBig());
		newPizzaRef.setWeightForSmall(newPizzaRefDTO.getWeightForSmall());
		newPizzaRef.setWeightForBig(newPizzaRefDTO.getWeightForBig());

		pizzaRefValidator.validate(newPizzaRef, bindingResult);
		if (bindingResult.hasErrors()) {
			ErrorResponse.makeErrorResponse(bindingResult);
		}
		ingredients.forEach(ingredient -> ingredient.getPizzaRefList().add(newPizzaRef));
		return pizzaRefService.savePizzaRef(newPizzaRef);
	}

	@PutMapping("/{id}")
	public PizzaRef putPizzaRef(@PathVariable("id") int id,
								  @RequestBody @Valid PizzaRefDTO putPizzaRefDTO,
								  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ErrorResponse.makeErrorResponse(bindingResult);
		}
		PizzaRef putPizzaRef = pizzaRefService.getPizzaRef(id);
		List<Ingredient> ingredients = ingredientService.getIngredientsByNames(putPizzaRefDTO.getNamesOfIngredients());

		putPizzaRef.setIngredients(ingredients);
		putPizzaRef.setPriceForSmall(putPizzaRefDTO.getPriceForSmall());
		putPizzaRef.setPriceForBig(putPizzaRefDTO.getPriceForBig());
		putPizzaRef.setWeightForSmall(putPizzaRefDTO.getWeightForSmall());
		putPizzaRef.setWeightForBig(putPizzaRefDTO.getWeightForBig());

		pizzaRefValidator.validate(putPizzaRef, bindingResult);
		if (bindingResult.hasErrors()) {
			ErrorResponse.makeErrorResponse(bindingResult);
		}
		ingredients.forEach(ingredient -> ingredient.getPizzaRefList().add(putPizzaRef));
		return pizzaRefService.savePizzaRef(putPizzaRef);
	}

	@ExceptionHandler({EntityNotFoundException.class, EntityNotSavedException.class, EmptyEntityListException.class})
	private ResponseEntity<ErrorResponse> handleException(RuntimeException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(System.currentTimeMillis());
		if (exception instanceof EntityNotFoundException entityNotFoundException) {
			if (entityNotFoundException.getId() != 0) {
				errorResponse.setMessage("Піцу з id-%d не знайдено!".formatted(entityNotFoundException.getId()));
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
			} else {
				errorResponse.setMessage("Не всі інгредієнти знайдено! Помилка у назві інгредієнта(ів)!");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
			}
		} else if (exception instanceof EntityNotSavedException entityNotSavedException) {
			errorResponse.setMessage(entityNotSavedException.getMessage());
		} else if (exception instanceof EmptyEntityListException) {
			errorResponse.setMessage("Не знайдено жодного з вказаних інгредієнтів!");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
