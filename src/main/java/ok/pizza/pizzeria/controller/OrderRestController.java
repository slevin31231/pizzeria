package ok.pizza.pizzeria.controller;

import ok.pizza.pizzeria.entity.Order;
import ok.pizza.pizzeria.service.OrderService;
import ok.pizza.pizzeria.util.EmptyEntityListException;
import ok.pizza.pizzeria.util.EntityNotFoundException;
import ok.pizza.pizzeria.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

	private final OrderService orderService;

	@Autowired
	public OrderRestController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public List<Order> getOrders() {
		return orderService.getOrders();
	}

	@GetMapping("/{id}")
	public Order getOrder(@PathVariable("id") int id) {
		return orderService.getOrder(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable("id") int id) {
		orderService.deleteOrder(id);
		return ResponseEntity.ok("Order with id-%d successfully deleted!".formatted(id));
	}

	@ExceptionHandler({EntityNotFoundException.class, EmptyEntityListException.class})
	private ResponseEntity<ErrorResponse> handleException(RuntimeException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		if (exception instanceof EntityNotFoundException entityNotFoundException) {
			errorResponse.setMessage("Замовлення з id-%d не знайдено!".formatted(entityNotFoundException.getId()));
		} else if (exception instanceof EmptyEntityListException) {
			errorResponse.setMessage("Немає замовлень!");
		}
		errorResponse.setTimestamp(System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
}
