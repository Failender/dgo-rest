package de.failender.dgo.graal;

import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        Test t = new Test();

        t.setSomeValue("Hello World!");
        UserEntity user = new UserRepositoryService().findUserByName("Failender");
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.json(user));
    }
}