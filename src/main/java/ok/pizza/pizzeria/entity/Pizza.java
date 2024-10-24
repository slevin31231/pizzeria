package ok.pizza.pizzeria.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "pizza")
@Data
@ToString(exclude = {"pizzaRef", "order"})
@NoArgsConstructor
public class Pizza {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "pizza_ref_id", referencedColumnName = "id")
	private PizzaRef pizzaRef;

	@Column(name = "is_big")
	private boolean big;

	@Column(name = "weight")
	private int weight;

	@Column(name = "price")
	private int price;

	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;
}
