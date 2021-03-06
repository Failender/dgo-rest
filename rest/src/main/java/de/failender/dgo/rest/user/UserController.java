package de.failender.dgo.rest.user;

import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.rest.security.DgoSecurity;
import de.failender.dgo.security.DgoSecurityContext;
import io.javalin.Context;
import io.javalin.Javalin;

public class UserController {

    private static final String PREFIX = "/api/user/";
    private static final String CREATE = PREFIX + "register";

    public UserController(Javalin app) {
        app.post(CREATE, this::registerUser);
        app.post(PREFIX, this::registerUsers);
    }

    private void registerUser(Context context) {
        UserRegistration registration = context.bodyAsClass(UserRegistration.class);
        DgoSecurityContext.checkPermission(DgoSecurity.CREATE_USER);
        UserEntity userEntity = UserService.registerUser(registration);
        if (userEntity == null) {
            context.status(409);
        }
    }

    private void registerUsers(Context context) {


        UserRegistration[] body = context.bodyAsClass(UserRegistration[].class);
        for (UserRegistration userRegistration : body) {
            try {
                UserService.registerUser(userRegistration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
