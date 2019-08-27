package de.failender.dgo.rest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.failender.dgo.persistance.pdf.PdfRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.security.DgoSecurityContext;
import de.failender.dgo.security.EntityNotFoundException;
import de.failender.dgo.security.NoPermissionException;
import de.failender.dgo.security.UserNotLoggedInException;
import io.javalin.Javalin;

import java.util.List;

public class DgoSecurity {

	public static final String PREFIX = "/api/security/";
	public static final String GENERATE = PREFIX + "generate";

	public static final String CREATE_USER = "CREATE_USER";


	public static void registerSecurity(Javalin app) {

		app.exception(UserNotLoggedInException.class, ((exception, ctx) -> {
			ctx.status(401);
		}));

		app.exception(NoPermissionException.class, ((exception, ctx) -> {
			ctx.status(403);
		}));

		app.exception(EntityNotFoundException.class, ((exception, ctx) -> {
			ctx.status(404);
			ctx.result(exception.getMessage());
		}));




		Algorithm algorithm = Algorithm.HMAC256("very_secret");
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer("dgo-rest")
				.build(); //Reusable verif

		app.before(context -> {

			String token = context.header("token");
			if (token == null) {
				token = context.queryParam("token");
			}

			if(token == null) {
				DgoSecurityContext.resetContext();
			} else {
				try {
					DecodedJWT jwt = verifier.verify(token);
					String username = jwt.getClaim("username").asString();

					List<String> permissions = jwt.getClaim("permissions").asList(String.class);
					DgoSecurityContext.login(username, permissions);
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
			List<String> visiblePdfs = PdfRepositoryService.getVisiblePdfs(userEntity);
			String token = JWT.create()
					.withClaim("username", username)
					.withArrayClaim("permissions", permissions.toArray(new String[0]))
					.withIssuer("dgo-rest")
					.sign(algorithm);
			context.header("token",token);
			context.header("permissions", String.join(",", permissions ));
			context.header("pdfs", String.join(",", visiblePdfs));
			context.header("access-control-expose-headers", "token,permissions,pdfs");
			context.json(permissions);

		});
	}




}
