package ok.pizza.pizzeria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ingredient")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final int id;

	@Column(name = "ingredient_name")
	private final String name;

	@Column(name = "ingredient_type")
	@Enumerated(EnumType.STRING)
	private final Type type;

	@ManyToMany(mappedBy = "ingredients")
	private List<Pizza> pizzaSet;

	public enum Type {
		MEAT, VEGETABLE, CHEESE, SEAFOOD;
	}
}
