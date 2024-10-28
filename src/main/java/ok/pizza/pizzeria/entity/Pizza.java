package ok.pizza.pizzeria.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "pizza")
@Data
@ToString(exclude = "order")
@NoArgsConstructor
@JsonIgnoreProperties("order")
public class Pizza {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "pizza_ref-id")
	private int pizzaRefId;

	@Column(name = "ingredients_names")
	private List<String> ingredientsNames;

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
