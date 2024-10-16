package ok.pizza.pizzeria.service;

import ok.pizza.pizzeria.entity.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

	private List<Order> orders = new ArrayList<>();

	public Order addOrder(Order order) {
		order.setDateTime(LocalDateTime.now());
		order.setDeliveryTime(LocalDateTime.now().plusMinutes(40));
		orders.add(order);
		return order;
	}
}
