package ok.pizza.pizzeria.controller;

import jakarta.validation.Valid;
import ok.pizza.pizzeria.dto.EmployeeDTO;
import ok.pizza.pizzeria.secutity.JWTUtil;
import ok.pizza.pizzeria.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

	private final JWTUtil jwtUtil;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthRestController(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public String performLogin(@RequestBody @Valid EmployeeDTO employeeDTO) {

		UsernamePasswordAuthenticationToken authToken =
				new UsernamePasswordAuthenticationToken(employeeDTO.getName(), employeeDTO.getPassword());

			authenticationManager.authenticate(authToken);

		return "Bearer %s".formatted(jwtUtil.generateToken(employeeDTO.getName(), employeeDTO.getRole()));

	}

	@ExceptionHandler({MethodArgumentNotValidException.class, BadCredentialsException.class})
	private ResponseEntity<ErrorResponse> handleException(Exception exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(System.currentTimeMillis());
		if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
			StringBuilder stringBuilder = new StringBuilder();
			methodArgumentNotValidException	.getBindingResult()
											.getFieldErrors()
											.forEach(error -> stringBuilder
																.append(error.getField())
																.append(" - ")
																.append(error.getDefaultMessage())
																.append("! "));
			errorResponse.setMessage(stringBuilder.toString().trim());
		} else if (exception instanceof BadCredentialsException) {
			errorResponse.setMessage("Реквізити не пройшли аутентифікацію! Не вірно вказані реквізити!");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
