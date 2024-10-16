package ok.pizza.pizzeria.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order {

	private LocalDateTime dateTime;
	private LocalDateTime deliveryTime;
	private String customerName;
	private String customerPhoneNumber;
	private String deliveryAddress;
	private String ccNumber;
	private String ccExpiration;
	private String ccVV;
	private int price;

	private List<Pizza> pizzaList = new ArrayList<>();

	public void addPizza(Pizza pizza) {
		pizzaList.add(pizza);
	}

	public int calculateValue() {
		price = 0;
		for (Pizza pizza : pizzaList) {
			if (pizza.isBig()) {
				price += pizza.getPriceForBig();
			} else {
				price += pizza.getPriceForSmall();
			}
		}
		return price;
	}

	public void deletePizzaFromOrder(int num) {
		pizzaList.remove(num - 1);
	}
}
