package de.failender.dgo.rest;

import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.rest.helden.HeldenController;
import de.failender.dgo.rest.pdf.PdfController;
import de.failender.dgo.rest.security.DgoSecurity;
import de.failender.dgo.rest.user.UserService;
import de.failender.ezql.EzqlConnector;
import de.failender.ezql.properties.PropertyReader;
import io.javalin.Javalin;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class DgoRest {

    public static void main(String[] args) throws IOException {

		long start = System.nanoTime();

        PropertyReader.initialize(args);
        EzqlConnector.connect("hibernate.connection");
        if(PropertyReader.getProperty("hibernate.initialize.on.start").equals("true")) {
            String sql = IOUtils.toString(DgoRest.class.getResourceAsStream("/setup.sql"), "UTF-8");
            EzqlConnector.execute(sql);

        }
        Javalin app = Javalin.create()
                .enableCorsForAllOrigins()
                .start(7000);
        app.get("/", ctx -> ctx.json(UserRepositoryService.findUserByName("Failender")));

        DgoSecurity.registerSecurity(app);
        UserService.initialize();
        new HeldenController(app);
        new PdfController(app);

		double elapsedTimeInSecond = (double) (System.nanoTime() - start) / 1_000_000_000;
		long takenMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		double mb = 1024 * 1024;
		System.out.printf("DGO Online in %f seconds, using %f MB ram", elapsedTimeInSecond, takenMemory / mb);

    }
}
