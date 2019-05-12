package de.failender.dgo.rest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import io.javalin.Javalin;

import java.util.List;

public class DgoSecurity {

	public static final String PREFIX = "/api/security/";
	public static final String GENERATE = PREFIX + "generate";

	private static ThreadLocal<SecurityContext> contextThreadLocal = new ThreadLocal<>();

	public static void registerSecurity(Javalin app) {

		app.exception(UserNotLoggedInException.class, ((exception, ctx) -> {
			ctx.status(401);
		}));
		Algorithm algorithm = Algorithm.HMAC256("very_secret");
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer("dgo-rest")
				.build(); //Reusable verif

		app.before(context -> {

			String token = context.header("token");

			if(token == null) {
				contextThreadLocal.set(null);
			} else {
				try {
					DecodedJWT jwt = verifier.verify(token);
					String username = jwt.getClaim("username").asString();
					List<String> permissions = jwt.getClaim("permissions").asList(String.class);
					contextThreadLocal.set(new SecurityContext(UserRepositoryService.findUserByName(username), permissions));
				} catch(InvalidClaimException e) {
					context.status(401);
				}

			}
		});


		app.get(GENERATE, context -> {

			String username = context.header("username");
			UserEntity userEntity = UserRepositoryService.findUserByName(username);

			String password = context.header("password");
			if(userEntity == null || !userEntity.getPassword().equals(password)) {
				context.status(401);
				return;
			}
			List<String> permissions = UserRepositoryService.findUserPermissions(userEntity);
			String token = JWT.create()
					.withClaim("username", username)
					.withArrayClaim("permissions", permissions.toArray(new String[0]))
					.withIssuer("dgo-rest")
					.sign(algorithm);
			context.header("token",token);
			context.header("permissions", String.join(",", permissions ));
			context.header("access-control-expose-headers", "token,permissions");
			context.json(permissions);

		});
	}

	public static UserEntity getAuthenticatedUser() {
		SecurityContext ctx = contextThreadLocal.get();
		if (ctx == null) {
			throw new UserNotLoggedInException();
		}
		return ctx.userEntity;
	}

	private static class SecurityContext {
		private final UserEntity userEntity;
		private final List<String> permissions;

		public SecurityContext(UserEntity userEntity, List<String> permissions) {
			this.userEntity = userEntity;
			this.permissions = permissions;
		}

		public UserEntity getUserEntity() {
			return userEntity;
		}
	}
}
