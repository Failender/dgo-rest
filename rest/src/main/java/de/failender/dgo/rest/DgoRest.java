package de.failender.dgo.rest;

import de.failender.dgo.persistance.HibernateUtil;
import de.failender.dgo.persistance.user.UserRepositoryService;
import io.javalin.Javalin;

public class DgoRest {

    public static void main(String[] args) {
        Test t = new Test();

        t.setSomeValue("Hello World!");

        HibernateUtil.getSessionFactory();
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.json(new UserRepositoryService().findUserByName("Failender")));

    }
}