package de.failender.dgo.graal;

import de.failender.dgo.user.HibernateUtil;
import de.failender.dgo.user.UserDao;
import de.failender.dgo.user.UserEntity;
import io.javalin.Javalin;
import org.hibernate.SessionFactory;
import org.omg.CORBA.UserException;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Test t = new Test();

        t.setSomeValue("Hello World!");
        List<UserEntity> users = new UserDao().getUser();
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.json(users));
    }
}