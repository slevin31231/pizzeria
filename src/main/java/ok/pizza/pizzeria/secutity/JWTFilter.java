package ok.pizza.pizzeria.secutity;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ok.pizza.pizzeria.service.EmployeeDetailsService;
import ok.pizza.pizzeria.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Map;

@Component
public class JWTFilter extends OncePerRequestFilter {

	private final EmployeeDetailsService employeeDetailsService;
	private final JWTUtil jwtUtil;
	private final ObjectMapper objectMapper;

	@Autowired
	public JWTFilter(EmployeeDetailsService employeeDetailsService, JWTUtil jwtUtil, ObjectMapper objectMapper) {
		this.employeeDetailsService = employeeDetailsService;
		this.jwtUtil = jwtUtil;
		this.objectMapper = objectMapper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);

			try {
				Map<String, String> claims = jwtUtil.validateTokenAndRetrieveClaims(jwt);
				String tokenUsername = claims.get("name");
				String tokenRole = claims.get("role");

				UserDetails userDetails = employeeDetailsService.loadUserByUsername(tokenUsername);
				String userRole = userDetails	.getAuthorities()
												.stream()
												.map(GrantedAuthority::getAuthority)
												.findFirst()
												.orElseThrow(() -> new AccessDeniedException("Помилка в ролі користувача в БД!"));

				if (!tokenRole.equals(userRole)) {
					throw new AccessDeniedException("Роль в JWT не співпадає з роллю користувача!");
				}

				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}

			} catch (JWTVerificationException exception) {
				sendCustomErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Помилка верифікації JWT!");
				return;
			} catch (UsernameNotFoundException | AccessDeniedException exception) {
				sendCustomErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
				return;
			}
		}
		filterChain.doFilter(request,response);
	}

	private void sendCustomErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(message);
		errorResponse.setTimestamp(System.currentTimeMillis());

		response.setStatus(statusCode);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		response.getWriter().flush();
	}
}
