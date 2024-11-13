package ok.pizza.pizzeria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ok.pizza.pizzeria.entity.Employee;
import ok.pizza.pizzeria.util.ValidEnum;

@Data
public class EmployeeDTO {

	@NotBlank(message = "Ім'я має бути заповненим")
	private String name;

	@NotBlank(message = "Пароль має бути заповненим")
	private String password;

	@NotNull(message = "Роль має бути вказана")
	@ValidEnum(enumClass = Employee.Role.class)
	private String role;
}
