package ok.pizza.pizzeria.repository;

import ok.pizza.pizzeria.entity.PizzaRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRefRepository extends JpaRepository<PizzaRef, Integer> {
}
