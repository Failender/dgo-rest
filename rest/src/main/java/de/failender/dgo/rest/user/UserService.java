package de.failender.dgo.rest.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.failender.dgo.persistance.HibernateUtil;
import de.failender.dgo.persistance.gruppe.GruppeEntity;
import de.failender.dgo.persistance.gruppe.GruppeRepository;
import de.failender.dgo.persistance.gruppe.GruppeRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.rest.integration.Beans;
import de.failender.heldensoftware.api.requests.PermissionRequest;
import de.failender.heldensoftware.xml.currentrights.Recht;
import de.failender.heldensoftware.xml.currentrights.Rechte;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserService {

	public static void initialize() {
		InputStream is = UserService.class.getResourceAsStream("/user.json");
		if(is != null) {
			try {
				List<UserRegistration> registrations = new ObjectMapper().readValue(is, new TypeReference<List<UserRegistration>>(){});
				for (UserRegistration registration : registrations) {
					registerUser(registration);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static UserEntity registerUser(UserRegistration userRegistration) {
		if (userRegistration.getName() == null || userRegistration.getGruppe() == null) {
			throw new RuntimeException("User or group cant be null");
		}
		if (UserRepositoryService.existsByName(userRegistration.getName())) {
			throw new RuntimeException("User already exists");
		}
		GruppeEntity gruppeEntity = GruppeRepository.findByName(userRegistration.getGruppe());
		if (gruppeEntity == null) {
			gruppeEntity = new GruppeEntity();
			gruppeEntity.setName(userRegistration.getGruppe());
			GruppeRepositoryService.save(gruppeEntity);
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setGruppe(gruppeEntity);
		userEntity.setName(userRegistration.getName());
		userEntity.setToken(userRegistration.getToken());
		if (userRegistration.getPassword() != null && !userRegistration.getPassword().isEmpty()) {
			userEntity.setPassword(userRegistration.getPassword());
		}
		if(userEntity.getToken() != null) {
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
		updateHeldenForUser();
		return userEntity;
	}

	public static void updateHeldenForUser() {

	}
}
