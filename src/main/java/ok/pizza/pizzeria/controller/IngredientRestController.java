package ok.pizza.pizzeria.controller;

import jakarta.validation.Valid;
import ok.pizza.pizzeria.dto.IngredientDto;
import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.service.IngredientService;
import ok.pizza.pizzeria.util.EntityNotFoundException;
import ok.pizza.pizzeria.util.EntityNotSavedException;
import ok.pizza.pizzeria.util.ErrorResponse;
import ok.pizza.pizzeria.util.IngredientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientRestController {

	private final IngredientService ingredientService;
	private final IngredientValidator ingredientValidator;

	@Autowired
	public IngredientRestController(IngredientService ingredientService, IngredientValidator ingredientValidator) {
		this.ingredientService = ingredientService;
		this.ingredientValidator = ingredientValidator;
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

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Ingredient postIngredient(@RequestBody @Valid IngredientDto newIngredientDto,
									 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ErrorResponse.makeErrorResponse(bindingResult);
		}
		Ingredient newIngredient = new Ingredient();
		newIngredient.setName(newIngredientDto.getName());
		newIngredient.setType(Ingredient.Type.valueOf(newIngredientDto.getType()));

		ingredientValidator.validate(newIngredient, bindingResult);
		if (bindingResult.hasErrors()) {
			ErrorResponse.makeErrorResponse(bindingResult);
		}
		return ingredientService.saveIngredient(newIngredient);
	}

	@PutMapping("/{id}")
	public Ingredient putIngredient(@PathVariable("id") int id,
									  @RequestBody @Valid IngredientDto putIngredientDto,
									  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ErrorResponse.makeErrorResponse(bindingResult);
		}
		Ingredient putIngredient = ingredientService.getIngredient(id);
		putIngredient.setName(putIngredientDto.getName());
		putIngredient.setType(Ingredient.Type.valueOf(putIngredientDto.getType()));

		ingredientValidator.validate(putIngredient, bindingResult);
		if (bindingResult.hasErrors()) {
			ErrorResponse.makeErrorResponse(bindingResult);
		}
		return ingredientService.saveIngredient(putIngredient);
	}

	@ExceptionHandler({EntityNotFoundException.class, EntityNotSavedException.class})
	private ResponseEntity<ErrorResponse> handleException(RuntimeException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setDateTime(LocalDateTime.now());
		if (exception instanceof EntityNotFoundException entityNotFoundException) {
			errorResponse.setMessage("Інгредієнт з id-%d не знайдено!".formatted(entityNotFoundException.getId()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} else if (exception instanceof EntityNotSavedException entityNotSavedException) {
			errorResponse.setMessage(entityNotSavedException.getMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
