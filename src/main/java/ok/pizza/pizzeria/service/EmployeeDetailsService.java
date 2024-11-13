package ok.pizza.pizzeria.service;

import ok.pizza.pizzeria.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsService implements UserDetailsService {

	private final EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeDetailsService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return employeeRepository.findEmployeeByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("Користувача з іменем %s не знайдено!".formatted(username)));
	}
}
