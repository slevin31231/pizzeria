package ok.pizza.pizzeria.entity;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.Set;

//@Entity
//@Table(name = "pizza")

@Data
@NoArgsConstructor
public class Pizza implements Cloneable {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	@OneToMany
	@Size(min = 1, max = 7, message = "Кількість інгредієнтів повинна бути від 1 до 7")
	private Set<Ingredient> ingredients;

	private boolean big;

	private int priceForSmall;
	private int priceForBig;

	private int weightForSmall;
	private int weightForBig;

	public Pizza(int id, Set<Ingredient> ingredients, int priceForSmall, int priceForBig, int weightForSmall, int weightForBig) {
		this.id = id;
		this.ingredients = ingredients;
		this.priceForSmall = priceForSmall;
		this.priceForBig = priceForBig;
		this.weightForSmall = weightForSmall;
		this.weightForBig = weightForBig;
	}

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

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

