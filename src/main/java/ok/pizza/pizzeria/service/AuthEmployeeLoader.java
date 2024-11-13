package ok.pizza.pizzeria.service;

import ok.pizza.pizzeria.entity.Employee;
import ok.pizza.pizzeria.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthEmployeeLoader implements ApplicationRunner {

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthEmployeeLoader(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		employeeRepository.save(
				Employee.builder()
						.name("PizzeriaAdministrator")
						.password(passwordEncoder.encode("AdminPass"))
						.role(Employee.Role.ROLE_ADMIN)
						.build()
		);
		employeeRepository.save(
				Employee.builder()
						.name("PizzeriaEmployee")
						.password(passwordEncoder.encode("EmployeePass"))
						.role(Employee.Role.ROLE_USER)
						.build()
		);
	}
}
