package ok.pizza.pizzeria.repository;

import ok.pizza.pizzeria.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

	Optional<Ingredient> findIngredientByNameAndType(String name, Ingredient.Type type);
	List<Ingredient> findAllIngredientsByNameIn(List<String> names);
}
