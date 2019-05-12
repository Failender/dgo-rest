package de.failender.dgo.rest.user;

import de.failender.dgo.rest.security.DgoSecurity;
import io.javalin.Context;
import io.javalin.Javalin;

public class UserController {

    private static final String PREFIX = "/api/user/";
    private static final String CREATE = PREFIX + "register";

    public UserController(Javalin app) {
        app.post(CREATE, this::registerUser);
    }

    private void registerUser(Context context) {
        UserRegistration registration = context.bodyAsClass(UserRegistration.class);
        DgoSecurity.checkPermission(DgoSecurity.CREATE_USER);
        UserService.registerUser(registration);
    }
}
