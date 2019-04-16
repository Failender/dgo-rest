package de.failender.dgo.rest;

import de.failender.dgo.persistance.HibernateUtil;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepository;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.rest.integration.Beans;
import de.failender.dgo.rest.security.DgoSecurity;
import de.failender.heldensoftware.api.authentication.TokenAuthentication;
import de.failender.heldensoftware.api.requests.GetAllHeldenRequest;
import de.failender.heldensoftware.xml.heldenliste.Helden;
import io.javalin.Javalin;

import java.math.BigInteger;
import java.util.List;

public class DgoRest {

    public static void main(String[] args) {


        HibernateUtil.getSessionFactory();
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.json(new UserRepositoryService().findUserByName("Failender")));

        DgoSecurity.registerSecurity(app);
        Helden helden = Beans.HELDEN_API.request(new GetAllHeldenRequest(new TokenAuthentication(new UserRepositoryService().findUserByName("Failender").getToken()))).block();

        List<String> permissions = new UserRepositoryService().findUserPermissions(new UserRepositoryService().findUserByName("Admin"));
        System.out.println(permissions);

        VersionEntity versionEntity = new VersionRepository().findFirstByHeldidOrderByVersionDesc(BigInteger.valueOf(36222));
        System.out.println(versionEntity);

    }
}