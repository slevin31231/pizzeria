package ok.pizza.pizzeria.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "ingredient")
@Data
@ToString(exclude = "pizzaRefList")
@RequiredArgsConstructor
@JsonIgnoreProperties("pizzaRefList")
public class Ingredient {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "ingredient_name")
	private String name;

	@Column(name = "ingredient_type")
	@Enumerated(EnumType.STRING)
	private Type type;

	@ManyToMany(mappedBy = "ingredients")
	private List<PizzaRef> pizzaRefList;

	public enum Type {
		MEAT, VEGETABLE, CHEESE, SEAFOOD;
	}
}
