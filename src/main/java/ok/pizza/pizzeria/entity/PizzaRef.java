package ok.pizza.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Random;

@Entity
@Table(name = "pizza_ref")
@Data
@ToString(exclude = "ingredients")
@NoArgsConstructor
public class PizzaRef {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "pizza_ref_ingredient",
				joinColumns = @JoinColumn(name = "pizza_ref_id"),
				inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	@Size(min = 1, max = 7, message = "Кількість інгредієнтів повинна бути від 1 до 7")
	private List<Ingredient> ingredients;

	@Column(name = "price_for_small")
	private int priceForSmall;

	@Column(name = "price_for_big")
	private int priceForBig;

	@Column(name = "weight_for_small")
	private int weightForSmall;

	@Column(name = "weight_for_big")
	private int weightForBig;

	public void setPriceAndWeight() {
		int quantity = ingredients.size();
		if (quantity == 1) {
			this.priceForSmall = getNumberWithStep(100, 150, 10);
			this.priceForBig = getNumberWithStep(130, 190, 10);
			this.weightForSmall = getNumberWithStep(540, 640, 10);
			this.weightForBig = getNumberWithStep(715, 865, 5);
		} else if (quantity == 2 || quantity == 3) {
			this.priceForSmall = getNumberWithStep(130, 170, 10);
			this.priceForBig = getNumberWithStep(170, 210, 10);
			this.weightForSmall = getNumberWithStep(640, 680, 10);
			this.weightForBig = getNumberWithStep(845, 915, 5);
		} else if (quantity == 4) {
			this.priceForSmall = getNumberWithStep(140, 180, 10);
			this.priceForBig = getNumberWithStep(180, 220, 10);
			this.weightForSmall = getNumberWithStep(605, 700, 10);
			this.weightForBig = getNumberWithStep(795, 935, 5);
		} else {
			this.priceForSmall = 180;
			this.priceForBig = 220;
			this.weightForSmall = 700;
			this.weightForBig = 935;
		}
	}

	private static int getNumberWithStep(int min, int max, int step) {
		Random random = new Random();
		int range = (max - min) / step;
		int randomIndex = random.nextInt(range + 1);
		return min + randomIndex * step;
	}

	public Pizza createPizza(boolean big) {
		Pizza pizza = new Pizza();
		pizza.setPizzaRefId(this.getId());

		List<String> ingredientsNames = this.getIngredients()
											.stream()
											.map(Ingredient::getName)
											.toList();

		pizza.setIngredientsNames(ingredientsNames);
		pizza.setBig(big);
		if (big) {
			pizza.setWeight(weightForBig);
			pizza.setPrice(priceForBig);
		} else {
			pizza.setWeight(weightForSmall);
			pizza.setPrice(priceForSmall);
		}
		return pizza;
	}
}

