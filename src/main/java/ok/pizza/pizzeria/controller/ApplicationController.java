package ok.pizza.pizzeria.controller;

import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.entity.Order;
import ok.pizza.pizzeria.entity.Pizza;
import ok.pizza.pizzeria.service.OrderService;
import ok.pizza.pizzeria.service.PizzaService;
import ok.pizza.pizzeria.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("order")
public class ApplicationController {

	private final IngredientService ingredientService;
	private final PizzaService pizzaService;
	private final OrderService orderService;

	@Autowired
	public ApplicationController(IngredientService ingredientService, PizzaService pizzaService, OrderService orderService) {
		this.ingredientService = ingredientService;
		this.pizzaService = pizzaService;
		this.orderService = orderService;
	}

	@GetMapping("/")
	public String showHomePage() {
		return "home";
	}

	@PostMapping("/select_pizza")
	public String selectPizza(@RequestParam("pizza_id") int pizzaId,
							  @RequestParam("big") boolean big,
							  @ModelAttribute Order order) {
		Pizza pizza = pizzaService.findByIdAndCopy(pizzaId, big);
		order.addPizza(pizza);
		return "redirect:/order";
	}


	@PostMapping("/create_pizza")
	public String createPizza(Pizza newPizza, @ModelAttribute Order order) {
		Pizza pizza = pizzaService.getOrCreateAndCopy(newPizza);
		order.addPizza(pizza);
		return "redirect:/order";
	}

	@GetMapping("/order")
	public String showOrderPage(Order order) {
		return "order";
	}

	@DeleteMapping("/order/delete")
	public String deletePizzaFromOrder(@RequestParam int index, @ModelAttribute Order order) {
		order.getPizzaList().remove(index);
		return "redirect:/order";
	}

	@PostMapping("/order")
	public String addOrder(@ModelAttribute Order order) {
		orderService.addOrder(order);
		System.out.println("Order: " + order + " is added to DB");
		return "redirect:/details";
	}

	@GetMapping("/details")
	public String showOrderDetailsPage(@ModelAttribute Order order, SessionStatus sessionStatus) {
		System.out.println(order.getDateTime());
		System.out.println(order.getDeliveryTime());
		sessionStatus.setComplete();
		return "details";
	}

	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("ingredients", ingredients);

		Ingredient.Type[] types = Ingredient.Type.values();
		for (Ingredient.Type type : types) {
			model.addAttribute(
					type.toString().toLowerCase(),
					filterByType(ingredients, type)
			);
		}
	}

	@ModelAttribute("pizza_set")
	public Set<Pizza> pizzaSet() {
		return pizzaService.getPizzaSet();
	}

	@ModelAttribute("pizza")
	public Pizza pizza() {
		return new Pizza();
	}

	@ModelAttribute("order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute("today")
	public Calendar today() {
		return Calendar.getInstance();
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).toList();
	}
}

