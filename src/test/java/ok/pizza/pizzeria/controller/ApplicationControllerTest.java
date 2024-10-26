package ok.pizza.pizzeria.controller;

import ok.pizza.pizzeria.service.IngredientService;
import ok.pizza.pizzeria.service.OrderService;
import ok.pizza.pizzeria.service.PizzaRefService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ApplicationController.class)
public class ApplicationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IngredientService ingredientService;

	@MockBean
	private OrderService orderService;

	@MockBean
	private PizzaRefService pizzaRefService;

	@Test
	public void testHomePage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("home"));
	}
}
