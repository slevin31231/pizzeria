package ok.pizza.pizzeria.controller;

import jakarta.validation.Valid;
import ok.pizza.pizzeria.entity.Ingredient;
import ok.pizza.pizzeria.entity.Order;
import ok.pizza.pizzeria.entity.Pizza;
import ok.pizza.pizzeria.entity.PizzaRef;
import ok.pizza.pizzeria.service.IngredientService;
import ok.pizza.pizzeria.service.OrderService;
import ok.pizza.pizzeria.service.PizzaRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("order")
public class ApplicationController {

	private final IngredientService ingredientService;
	private final PizzaRefService pizzaRefService;
	private final OrderService orderService;

	@Autowired
	public ApplicationController(IngredientService ingredientService, PizzaRefService pizzaRefService, OrderService orderService) {
		this.ingredientService = ingredientService;
		this.pizzaRefService = pizzaRefService;
		this.orderService = orderService;
	}

	@GetMapping("/")
	public String showHomePage() {
		return "home";
	}

	@PostMapping("/select_pizza_ref")
	public String selectPizza(@RequestParam("pizza_ref_id") int pizzaRefId,
							  @RequestParam("big") boolean big,
							  @ModelAttribute Order order) {
		Pizza pizza = pizzaRefService.findByIdAndCopy(pizzaRefId, big);
		order.addPizza(pizza);
		return "redirect:/order";
	}

	@PostMapping("/create_pizza_ref")
	public String createPizza(@Valid PizzaRef newPizzaRef,
							  BindingResult bindingResult,
							  @RequestParam("big") boolean big,
							  @ModelAttribute Order order,
							  RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());
			return "redirect:/";
		}
		Pizza pizza = pizzaRefService.getOrCreateAndCopy(newPizzaRef, big);
		order.addPizza(pizza);
		return "redirect:/order";
	}

	@GetMapping("/order")
	public String showOrderPage(Order order) {
		return "order";
	}

	@DeleteMapping("/order/delete_pizza")
	public String deletePizzaFromOrder(@RequestParam int index,
									   @ModelAttribute Order order) {
		order.deletePizza(index);
		return "redirect:/order";
	}

	@PostMapping("/order")
	public String addOrder(@Valid @ModelAttribute Order order,
						   BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "order";
		orderService.saveOrder(order);
		return "redirect:/details";
	}

	@GetMapping("/details")
	public String showOrderDetailsPage(@ModelAttribute Order order,
									   SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "details";
	}

	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = ingredientService.getIngredients();
		model.addAttribute("ingredients", ingredients);

		Ingredient.Type[] types = Ingredient.Type.values();
		for (Ingredient.Type type : types) {
			model.addAttribute(
					type.toString().toLowerCase(),
					filterByType(ingredients, type)
			);
		}
	}

	@ModelAttribute("pizza_ref_list")
	public List<PizzaRef> pizzaRefList() {
		return pizzaRefService.getPizzaRefList();
	}

	@ModelAttribute("pizza_ref")
	public PizzaRef pizzaRef() {
		return new PizzaRef();
	}

	@ModelAttribute("order")
	public Order order() {
		return new Order();
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
		return ingredients.stream().filter(ingredient -> ingredient.getType().equals(type)).toList();
	}
}

