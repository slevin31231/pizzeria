package ok.pizza.pizzeria.secutity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ok.pizza.pizzeria.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Autowired
	public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}


	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Доступ до цього ресурсу дозволено лише для ROLE_ADMIN!");
		errorResponse.setTimestamp(System.currentTimeMillis());

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		response.getWriter().flush();
	}
}