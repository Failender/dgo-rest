package de.failender.dgo.rest;

import de.failender.dgo.persistance.HibernateUtil;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepository;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserMapper;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.rest.integration.Beans;
import de.failender.dgo.rest.security.DgoSecurity;
import de.failender.dgo.rest.user.UserService;
import de.failender.ezql.EzqlConnector;
import de.failender.heldensoftware.api.authentication.TokenAuthentication;
import de.failender.heldensoftware.api.requests.GetAllHeldenRequest;
import de.failender.heldensoftware.xml.heldenliste.Helden;
import io.javalin.Javalin;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class DgoRest {

    public static void main(String[] args) throws IOException {


        EzqlConnector.connect("hibernate.connection");
        String sql = IOUtils.toString(HibernateUtil.class.getResourceAsStream("/setup.sql"), "UTF-8");
        EzqlConnector.execute(sql);
        HibernateUtil.getSessionFactory();
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.json(UserRepositoryService.findUserByName("Failender")));

        DgoSecurity.registerSecurity(app);
        UserService.initialize();

        List<UserEntity> userEntities = UserRepositoryService.findAll();
        System.out.println(userEntities);
    }
}