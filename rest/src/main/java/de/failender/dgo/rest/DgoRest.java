package de.failender.dgo.rest;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepository;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.rest.security.DgoSecurity;
import de.failender.dgo.rest.user.UserService;
import de.failender.ezql.EzqlConnector;
import de.failender.ezql.properties.PropertyReader;
import io.javalin.Javalin;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

public class DgoRest {

    public static void main(String[] args) throws IOException {


        PropertyReader.initialize(args);
        EzqlConnector.connect("hibernate.connection");
        if(PropertyReader.getProperty("hibernate.initialize.on.start").equals("true")) {
            String sql = IOUtils.toString(DgoRest.class.getResourceAsStream("/setup.sql"), "UTF-8");
            EzqlConnector.execute(sql);

        }
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.json(UserRepositoryService.findUserByName("Failender")));

        DgoSecurity.registerSecurity(app);
        UserService.initialize();

    }
}