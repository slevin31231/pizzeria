package ok.pizza.pizzeria.service;

import lombok.Getter;
import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.entity.Pizza;
import ok.pizza.pizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PizzaService implements ApplicationRunner {

	private static int id;
	@Getter
	private Set<Pizza> pizzaSet;
	private Map<String, Ingredient> ingredientMap;
	private final IngredientRepository ingredientRepository;

	@Autowired
	public PizzaService(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.ingredientMap = fillIngredientsMap();
		this.pizzaSet = fillPizzaSet();
	}

	private Map<String, Ingredient> fillIngredientsMap() {
		return ingredientRepository.findAll().stream().collect(Collectors.toMap(Ingredient::getName, ingredient -> ingredient));
	}

	private Set<Pizza> fillPizzaSet() {
		Set<Pizza> pizzaSet = new LinkedHashSet<>();
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка"), 150, 190, 640, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Гриби", "Цибуля"), 130, 170, 640, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Гриби"), 150, 190, 670, 895));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Гриби", "Ананаси"), 150, 190, 670, 895));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Ковбаса", "Чилі"), 150, 190, 650, 915));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Ананаси"), 150, 190, 650, 845));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Мариновані огірки"), 150, 180, 660, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Пепероні", "Помідори"), 160, 200, 650, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Салямі", "Бекон"), 160, 200, 640, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Моцарелла"), 100, 130, 540, 715));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Пармезан", "Дорблю", "Моцарелла", "Вершки"), 160, 200, 605, 795));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Ковбаса", "Гриби"), 140, 180, 650, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Гриби", "Шинка", "Ковбаса", "Куряче філе"), 160, 200, 690, 935));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Куряче філе", "Горошок", "Кукурудза", "Маслини"), 150, 190, 650, 885));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Гриби", "Цибуля", "Кукурудза", "Маслини"), 140, 180, 670, 915));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Маслини"), 150, 190, 650, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Червона риба", "Кальмар", "Крабові палички", "Креветки"), 180, 220, 630, 845));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Куряче філе", "Кальмар", "Маслини"), 170, 210, 650, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Куряче філе", "Кальмар", "Мариновані огірки"), 150, 190, 650, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Тунець", "Лимон"), 160, 200, 630, 825));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Тунець", "Гриби"), 170, 210, 680, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Куряче філе", "Кукурудза", "Ананаси"), 160, 200, 700, 925));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Тунець", "Кальмар", "Лимон"), 180, 220, 660, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Тунець", "Мариновані огірки"), 170, 210, 640, 845));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Мисливські ковбаски"), 160, 200, 640, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Ковбаса", "Кукурудза"), 150, 190, 650, 875));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Куряче філе", "Кукурудза", "Оливки"), 160, 200, 670, 925));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Куряче філе", "Мариновані огірки"), 150, 190, 660, 885));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Шинка", "Гриби", "Горошок", "Кукурудза"), 150, 190, 660, 885));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Куряче філе", "Ананаси"), 150, 190, 640, 845));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Гриби", "Кукурудза", "Горошок", "Оливки"), 140, 180, 650, 875));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Гриби", "Цибуля", "Оливки"), 140, 180, 650, 865));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Помідори", "Гриби", "Перець болгарський", "Цибуля"), 140, 180, 660, 895));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Червона риба", "Кальмар", "Куряче філе"), 180, 220, 660, 795));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Червона риба", "Маслини", "Оливки"), 180, 220, 640, 835));
		pizzaSet.add(new Pizza(++id, getIngredientsByName("Помідори", "Орегано"), 130, 160, 640, 865));
		return pizzaSet;
	}

	private Set<Ingredient> getIngredientsByName(String... ing) {
		return Stream.of(ing).map(ingredientMap::get).collect(Collectors.toSet());
	}

	public Pizza findByIdAndCopy(int pizzaId, boolean big) {
		Pizza selectedPizza = pizzaSet.stream().filter(pizza -> pizza.getId() == pizzaId).findFirst().orElse(null);
		selectedPizza.setBig(big);
		Pizza pizzaCopy;
		try {
			pizzaCopy = (Pizza) selectedPizza.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return pizzaCopy;
	}

	public Pizza getOrCreateAndCopy(Pizza newPizza) {
		Set<Ingredient> ingredientSet = newPizza.getIngredients();
		for (Pizza pizza : pizzaSet) {
			if (pizza.getIngredients().equals(ingredientSet)) {
				pizza.setBig(newPizza.isBig());
				return pizza;
			}
		}
		newPizza.setId(++id);
		newPizza.setPriceAndWeight();
		pizzaSet.add(newPizza);
		Pizza pizzaCopy;
		try {
			pizzaCopy = (Pizza) newPizza.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return pizzaCopy;
	}
}
