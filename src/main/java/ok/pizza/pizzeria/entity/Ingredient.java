package ok.pizza.pizzeria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingredients")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final int id;

	@Column(name = "ingredient")
	private final String name;

	@Column(name = "ingredient_type")
	@Enumerated(EnumType.STRING)
	private final Type type;


	public enum Type {
		MEAT, VEGETABLE, CHEESE, SEAFOOD;
	}
}
