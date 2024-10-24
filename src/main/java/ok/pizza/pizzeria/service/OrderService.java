package ok.pizza.pizzeria.service;

import ok.pizza.pizzeria.entity.Order;
import ok.pizza.pizzeria.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
