package ok.pizza.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Data
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "date_time")
	private LocalDateTime dateTime;

	@Column(name = "delivery_time")
	private LocalDateTime deliveryTime;

	@Column(name = "customer_name")
	@NotEmpty(message = "Ім'я має бути заповненим")
	@Size(min = 1, max = 50, message = "Ім'я має бути від 1 до 50 символів")
	private String customerName;

	@Column(name = "customer_phone_number")
	@NotEmpty(message = "Номер телефону має бути заповненим")
	@Pattern(regexp = "^\\+380\\(?\\d{2}\\)?([- ]?\\d){7}$", message = "Невірний формат номеру телефону")
	private String customerPhoneNumber;

	@Column(name = "deliveryJ_address")
	@NotEmpty(message = "Адрес має бути заповненим")
	@Size(min = 1, max = 50, message = "Адрес має бути від 1 до 50 символів")
	private String deliveryAddress;

	@Column(name = "cc_number")
	@CreditCardNumber(message = "Невірний номер картки")
	private String ccNumber;

	@Column(name = "cc_expiration")
	@Pattern(regexp = "^(0[1-9]|1[1-2])(\\/)([2-9][0-9])$", message = "Невірний формат MM/YY")
	private String ccExpiration;

	@Digits(integer = 3, fraction = 0, message = "Невірний формат CVV")
	private String ccVV;

	@Column(name = "price")
	private int price;

	@ManyToMany
	@JoinTable(	name = "order_pizza",
				joinColumns = @JoinColumn(name = "order_id"),
				inverseJoinColumns = @JoinColumn(name = "pizza_id"))
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
