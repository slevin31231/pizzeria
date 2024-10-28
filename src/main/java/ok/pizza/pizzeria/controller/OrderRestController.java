package ok.pizza.pizzeria.controller;

import ok.pizza.pizzeria.entity.Order;
import ok.pizza.pizzeria.service.OrderService;
import ok.pizza.pizzeria.util.EmptyOrderListException;
import ok.pizza.pizzeria.util.ErrorResponse;
import ok.pizza.pizzeria.util.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

	@ExceptionHandler({OrderNotFoundException.class, EmptyOrderListException.class})
	private ResponseEntity<ErrorResponse> handleException(RuntimeException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		if (exception instanceof OrderNotFoundException orderNotFoundException) {
			errorResponse.setMessage("Order with id-%d wasn't found!".formatted(orderNotFoundException.getId()));
		} else if (exception instanceof EmptyOrderListException) {
			errorResponse.setMessage("Orders wasn't found!");
		}
		errorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
