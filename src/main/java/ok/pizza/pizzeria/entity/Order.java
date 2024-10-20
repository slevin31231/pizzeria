package ok.pizza.pizzeria.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order {

	private LocalDateTime dateTime;
	private LocalDateTime deliveryTime;

	@NotEmpty(message = "Ім'я має бути заповненим")
	private String customerName;

	@NotEmpty(message = "Номер телефону має бути заповненим")
	@Pattern(regexp = "^\\+380\\(?\\d{2}\\)?([- ]?\\d){7}$", message = "Номер телефону має починатися з +380...")
	private String customerPhoneNumber;

	@NotEmpty(message = "Адрес має бути заповненим")
	private String deliveryAddress;

	@CreditCardNumber(message = "Неправильний номер картки")
	private String ccNumber;

	@Pattern(regexp = "^(0[1-9]|1[1-2])(\\/)([2-9][0-9])$", message = "Неправильний формат MM/YY")
	private String ccExpiration;

	@Digits(integer = 3, fraction = 0, message = "Неправильний формат CVV")
	private String ccVV;

	private int price;

	@NotNull
	@Size(min = 1, max = 10, message = "Кількість піц повинна бути від 1 до 10")
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
