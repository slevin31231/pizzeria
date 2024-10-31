package ok.pizza.pizzeria.service;

import ok.pizza.pizzeria.entity.Order;
import ok.pizza.pizzeria.repository.OrderRepository;
import ok.pizza.pizzeria.util.EmptyEntityListException;
import ok.pizza.pizzeria.util.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

	private final OrderRepository orderRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order saveOrder(Order order) {
		order.setDateTime(LocalDateTime.now());
		order.setDeliveryTime(LocalDateTime.now().plusMinutes(40));
		return orderRepository.save(order);
	}

	public List<Order> getOrders() {
		List<Order> orders = orderRepository.findAll();
		if (orders.isEmpty()) {
			throw new EmptyEntityListException();
		}
		return orders;
	}

	public Order getOrder(int id) {
		Optional<Order> optionalOrder = orderRepository.findById(id);
		return optionalOrder.orElseThrow(() -> new EntityNotFoundException(id));
	}

	public void deleteOrder(int id) {
		if (orderRepository.existsById(id)) {
			orderRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException(id);
		}
	}
}
