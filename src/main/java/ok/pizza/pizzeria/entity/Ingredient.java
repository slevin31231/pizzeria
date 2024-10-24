package ok.pizza.pizzeria.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "ingredient")
@Data
@ToString(exclude = "pizzaRefList")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Ingredient {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final int id;

	@Column(name = "ingredient_name")
	private final String name;

	@Column(name = "ingredient_type")
	@Enumerated(EnumType.STRING)
	private final Type type;

	@ManyToMany(mappedBy = "ingredients")
	private List<PizzaRef> pizzaRefList;

	public enum Type {
		MEAT, VEGETABLE, CHEESE, SEAFOOD;
	}
}
