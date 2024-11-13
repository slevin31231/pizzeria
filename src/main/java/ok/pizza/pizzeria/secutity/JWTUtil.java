package ok.pizza.pizzeria.secutity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

	private static final String ISSUER = "okPizzeria";
	private static final String SUBJECT = "securityForREST";

	@Value("${jwt_secret}")
	private String secret;

	public String generateToken(String name, String role) {
		Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(15).toInstant());
		return JWT	.create()
					.withIssuer(ISSUER)
					.withSubject(SUBJECT)
					.withClaim("name", name)
					.withClaim("role", role)
					.withIssuedAt(new Date())
					.withExpiresAt(expirationDate)
					.sign(Algorithm.HMAC256(secret));
	}

	public Map<String, String> validateTokenAndRetrieveClaims(String token) throws JWTVerificationException {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
				.withIssuer(ISSUER)
				.withSubject(SUBJECT)
				.build();

		DecodedJWT decodedJWT = verifier.verify(token);

		Map<String, String> map = new HashMap<>();
		decodedJWT.getClaims().forEach((key, claim) -> map.put(key, claim.asString()));
		return map;
	}
}
