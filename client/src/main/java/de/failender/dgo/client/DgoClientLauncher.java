package de.failender.dgo.client;

import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.security.DgoSecurityContext;
import de.failender.ezql.EzqlConnector;
import de.failender.ezql.properties.PropertyReader;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class DgoClientLauncher {


    public static void main(String[] args) throws IOException {
        PropertyReader.initialize(args);
        EzqlConnector.initialize("hibernate.connection");
        if (PropertyReader.getProperty("hibernate.initialize.on.start").equals("true")) {
            System.out.println("Initializing db");
            EzqlConnector.allocateConnection();
            EzqlConnector.execute(IOUtils.toString(DgoClientLauncher.class.getResourceAsStream("/setup_h2.sql"), "UTF-8"));
            String adminInsert = "INSERT INTO USERS (NAME, PASSWORD, TOKEN, GRUPPE_ID, CAN_WRITE) VALUES ('Admin', 'pass', null, null, false)";
            EzqlConnector.execute(adminInsert);
            UserEntity admin = UserRepositoryService.findUserByName("Admin");
            UserRepositoryService.addRoleForUser(admin, 1L);
            EzqlConnector.releaseConnection();
        }
        DgoSecurityContext.permissionChecker = permission -> true;
        DgoClient.main(args);
    }

}
