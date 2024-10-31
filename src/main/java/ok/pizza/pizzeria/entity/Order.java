package ok.pizza.pizzeria.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Data
@ToString(exclude = "pizzaList")
@NoArgsConstructor
public class Order {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "date_time", length = 50)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime dateTime;

	@Column(name = "delivery_time", length = 50)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime deliveryTime;

	@Column(name = "customer_name", length = 50)
	@NotBlank(message = "Ім'я має бути заповненим")
	@Size(min = 1, max = 50, message = "Ім'я має бути від 1 до 50 символів")
	private String customerName;

	@Column(name = "customer_phone_number", length = 50)
	@NotBlank(message = "Номер телефону має бути заповненим")
	@Pattern(regexp = "^\\+380\\(?\\d{2}\\)?([- ]?\\d){7}$", message = "Невірний формат номеру телефону")
	private String customerPhoneNumber;

	@Column(name = "deliveryJ_address", length = 50)
	@NotBlank(message = "Адрес має бути заповненим")
	@Size(min = 1, max = 50, message = "Адрес має бути від 1 до 50 символів")
	private String deliveryAddress;

	@Column(name = "cc_number", length = 16)
	@CreditCardNumber(message = "Невірний номер картки")
	private String ccNumber;

	@Column(name = "cc_expiration", length = 5)
	@Pattern(regexp = "^(0[1-9]|1[1-2])(\\/)([2-9][0-9])$", message = "Невірний формат MM/YY")
	private String ccExpiration;

	@Column(name = "cc_vv", length = 3)
	@Digits(integer = 3, fraction = 0, message = "Невірний формат CVV")
	private String ccVV;

	@Column(name = "price")
	private int price;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@Size(min = 1, max = 10, message = "Кількість піц повинна бути від 1 до 10")
	private List<Pizza> pizzaList = new ArrayList<>();

	public void addPizza(Pizza pizza) {
		pizza.setOrder(this);
		pizzaList.add(pizza);
		this.calculateValue();
	}

	public void deletePizza(int index) {
		Pizza deletedPizza = this.getPizzaList().get(index);
		deletedPizza.setOrder(null);
		this.getPizzaList().remove(deletedPizza);
		this.calculateValue();
	}

	private void calculateValue() {
		price = 0;
		for (Pizza pizza : pizzaList)
			price += pizza.getPrice();
	}
}
