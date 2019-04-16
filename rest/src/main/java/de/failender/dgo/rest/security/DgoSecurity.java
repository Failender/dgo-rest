package de.failender.dgo.rest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import io.javalin.Javalin;

public class DgoSecurity {

	public static final String PREFIX = "/api/security/";
	public static final String GENERATE = PREFIX + "generate";

	private static ThreadLocal<SecurityContext> contextThreadLocal = new ThreadLocal<>();

	public static void registerSecurity(Javalin app) {

		Algorithm algorithm = Algorithm.HMAC256("very_secret");
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer("dgo-rest")
				.build(); //Reusable verif

		app.before(context -> {

			String token = context.header("token");
			System.out.println(token);

			if(token == null) {
				contextThreadLocal.set(null);
			} else {
				try {
					DecodedJWT jwt = verifier.verify(token);
					String username = jwt.getClaim("username").asString();
					System.out.println(username);
					contextThreadLocal.set(new SecurityContext(new UserRepositoryService().findUserByName(username)));
				} catch(InvalidClaimException e) {
					context.status(401);
				}

			}
		});


		app.get(GENERATE, context -> {

			String username = context.header("username");
			UserEntity userEntity = new UserRepositoryService().findUserByName(username);

			String password = context.header("password");
			if(!userEntity.getPassword().equals(password)) {
				context.status(401);
				return;
			}
			String token = JWT.create()
					.withClaim("username", username)
					.withIssuer("dgo-rest")
					.sign(algorithm);
			context.header("token",token);

		});
	}

	private static class SecurityContext {
		private UserEntity userEntity;

		public SecurityContext(UserEntity userEntity) {
			this.userEntity = userEntity;
		}

		public UserEntity getUserEntity() {
			return userEntity;
		}
	}
}
