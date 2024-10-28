package ok.pizza.pizzeria.controller;

import ok.pizza.pizzeria.entity.PizzaRef;
import ok.pizza.pizzeria.service.PizzaRefService;
import ok.pizza.pizzeria.util.ErrorResponse;
import ok.pizza.pizzeria.util.PizzaRefNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaRefRestController {

	private final PizzaRefService pizzaRefService;

	@Autowired
	public PizzaRefRestController(PizzaRefService pizzaRefService) {
		this.pizzaRefService = pizzaRefService;
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

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleException(PizzaRefNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Pizza with id-%d wasn't found!".formatted(exception.getId()));
		errorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
