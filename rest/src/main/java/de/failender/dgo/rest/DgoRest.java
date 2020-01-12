package de.failender.dgo.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.rest.asset.AssetController;
import de.failender.dgo.rest.gruppen.GruppeController;
import de.failender.dgo.rest.helden.HeldenController;
import de.failender.dgo.rest.helden.geld.GeldController;
import de.failender.dgo.rest.helden.inventar.HeldInventarController;
import de.failender.dgo.rest.helden.steigern.SteigernController;
import de.failender.dgo.rest.helden.uebersicht.HeldUebersichtController;
import de.failender.dgo.rest.helden.version.VersionController;
import de.failender.dgo.rest.helden.zauberspeicher.ZauberspeicherController;
import de.failender.dgo.rest.kampf.KampfController;
import de.failender.dgo.rest.meister.raumplan.RaumplanController;
import de.failender.dgo.rest.pdf.PdfController;
import de.failender.dgo.rest.security.DgoSecurity;
import de.failender.dgo.rest.synchronization.SynchronizationService;
import de.failender.dgo.rest.user.UserController;
import de.failender.dgo.rest.user.UserService;
import de.failender.ezql.EzqlConnector;
import de.failender.ezql.properties.PropertyReader;
import de.failender.ezql.repository.EzqlRepository;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class DgoRest {

    public static void main(String[] args) throws IOException {

		long start = System.nanoTime();

        PropertyReader.initialize(args);
        EzqlConnector.initialize("hibernate.connection");
        if(PropertyReader.getProperty("hibernate.initialize.on.start").equals("true")) {
            EzqlConnector.execute(IOUtils.toString(DgoRest.class.getResourceAsStream("/setup.sql"), "UTF-8"));
            //EzqlConnector.execute(IOUtils.toString(DgoRest.class.getResourceAsStream("/talente.sql"), "UTF-8"));
            String adminInsert = "INSERT INTO USERS (NAME, PASSWORD, TOKEN, GRUPPE_ID, CAN_WRITE) VALUES ('Admin', 'pass', null, null, false)";
            EzqlConnector.execute(adminInsert);
            UserEntity admin = UserRepositoryService.findUserByName("Admin");
            UserRepositoryService.addRoleForUser(admin, 1L);
        }
        //Migration for dev mode
        InputStream is = DgoRest.class.getResourceAsStream("/migration.sql");
        if(is != null) {
            String sql = IOUtils.toString(is, "UTF-8");
            EzqlConnector.execute(sql);
        }

        Javalin app = Javalin.create()
                .enableCorsForAllOrigins()
                .start(7000);

        app.before(ctx -> EzqlConnector.allocateConnection());
        app.after(ctx -> EzqlConnector.releaseConnection());

        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.findAndRegisterModules();
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JavalinJackson.configure(om);

        new HeldInventarController(app);
        new HeldenController(app);
        new PdfController(app);
		new GruppeController(app);
        new UserController(app);
        new ZauberspeicherController(app);
        new GeldController(app);
        new HeldUebersichtController(app);
        new RaumplanController(app);
        new KampfController(app);
        new SteigernController(app);
        new VersionController(app);
        new AssetController(app);

        DgoSecurity.registerSecurity(app);
        SynchronizationService.intialize(om);
        UserService.initialize();

        double elapsedTimeInSecond = (double) (System.nanoTime() - start) / 1_000_000_000;
		long takenMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		double mb = 1024 * 1024;
		System.out.printf("DGO Online in %f seconds, using %f MB ram", elapsedTimeInSecond, takenMemory / mb);
		System.out.println();

    }
}
