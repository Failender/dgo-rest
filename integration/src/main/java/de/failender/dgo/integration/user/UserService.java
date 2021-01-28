package de.failender.dgo.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.failender.dgo.integration.Beans;
import de.failender.dgo.integration.HeldenService;
import de.failender.dgo.persistance.gruppe.GruppeEntity;
import de.failender.dgo.persistance.gruppe.GruppeRepositoryService;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.heldensoftware.api.requests.PermissionRequest;
import de.failender.heldensoftware.xml.currentrights.Recht;
import de.failender.heldensoftware.xml.currentrights.Rechte;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    public static void initialize() {
        InputStream is = UserService.class.getResourceAsStream("/user.json");
        if (is != null) {
            try {
                List<UserRegistration> registrations = new ObjectMapper().readValue(is, new TypeReference<List<UserRegistration>>() {
                });
                for (UserRegistration registration : registrations) {
                    registerUser(registration);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static HeldenService.SynchronizationResult registerUser(UserRegistration userRegistration) {
        UserEntity userEntity = registerUser(userRegistration, true);
        if (userEntity != null) {
            return HeldenService.updateHeldenForUser(userEntity);
        }
        return null;
    }

    public static UserEntity registerUser(UserRegistration userRegistration, boolean syncHelden) {
        if (userRegistration.getName() == null || userRegistration.getGruppe() == null) {
            log.error("User or group cant be null");
            return null;
        }
        UserEntity userEntity = UserRepositoryService.findUserByName(userRegistration.getName());
        if (userEntity != null) {
            log.error("User already exists");
            if (Boolean.TRUE == userRegistration.getHeldenPublic()) {
                updateHeldenPublicForUser(userEntity);
            }
            return null;
        }
        GruppeEntity gruppeEntity = GruppeRepositoryService.findByName(userRegistration.getGruppe());
        if (gruppeEntity == null) {
            gruppeEntity = new GruppeEntity();
            gruppeEntity.setName(userRegistration.getGruppe());
            GruppeRepositoryService.save(gruppeEntity);
        }
        userEntity = new UserEntity();
        userEntity.setGruppe(gruppeEntity.getId());
        userEntity.setName(userRegistration.getName());
        userEntity.setToken(userRegistration.getToken());
        if (userRegistration.getPassword() != null && !userRegistration.getPassword().isEmpty()) {
            userEntity.setPassword(userRegistration.getPassword());
        }
        if (userEntity.getToken() != null) {
            Rechte rechte = Beans.HELDEN_API.request(new PermissionRequest(userEntity.getToken()))
                    .block();
            Recht recht = rechte.getRecht()
                    .stream()
                    .filter(r -> r.getName().equals("HeldenWrite"))
                    .findFirst()
                    .get();
            userEntity.setCanWrite(recht.getGranted());
        } else {
            userEntity.setCanWrite(false);
        }
        UserRepositoryService.save(userEntity);
        if (userRegistration.getRole() != null) {
            UserRepositoryService.addRoleForUser(userEntity, userRegistration.getRole());
        }
        if (syncHelden) {
            HeldenService.updateHeldenForUser(userEntity);
            if (Boolean.TRUE == userRegistration.getHeldenPublic()) {
                updateHeldenPublicForUser(userEntity);
            }
        }
        return userEntity;
    }

    private static void updateHeldenPublicForUser(UserEntity userEntity) {
        HeldRepositoryService.updatePublic(userEntity, true);
    }
}
