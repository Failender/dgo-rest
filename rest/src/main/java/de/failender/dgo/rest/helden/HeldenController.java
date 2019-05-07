package de.failender.dgo.rest.helden;

import de.failender.dgo.persistance.gruppe.GruppeEntity;
import de.failender.dgo.persistance.gruppe.GruppeRepository;
import de.failender.dgo.persistance.gruppe.GruppeRepositoryService;
import de.failender.dgo.persistance.held.*;
import de.failender.dgo.rest.integration.Beans;
import de.failender.dgo.rest.security.DgoSecurity;
import de.failender.heldensoftware.api.requests.ReturnHeldDatenWithEreignisseRequest;
import de.failender.heldensoftware.xml.datenxml.Daten;
import io.javalin.Context;
import io.javalin.Javalin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HeldenController {

	public static final String PREFIX = "api/helden/";
	public static final String FOR_USER = PREFIX + "user/:user";
	public static final String MEINE_HELDEN = PREFIX + "meine";
	public static final String HELD = PREFIX + "held/:held";
	public static final String UPDATE_PUBLIC = PREFIX + "held/:held/public/:public";
	public static final String UPDATE_ACTIVE = PREFIX + "held/:held/active/:active";

	public HeldenController(Javalin javalin) {

		javalin.get(FOR_USER, this::getHeldenForUser);
		javalin.get(MEINE_HELDEN, this::getMeineHelden);
		javalin.get(HELD, this::getHeld);
		javalin.put(UPDATE_ACTIVE, this::updateActive);
		javalin.put(UPDATE_PUBLIC, this::updatePublic);
	}

	private void getHeld(Context context) {
		Long held = Long.valueOf(context.pathParam("held"));
		HeldEntity heldEntity = HeldRepositoryService.findById(held);
		VersionEntity latest = VersionRepositoryService.findLatestVersion(heldEntity);
		Daten daten = Beans.HELDEN_API.request(new ReturnHeldDatenWithEreignisseRequest(heldEntity.getId(), null, latest.getCacheId()))
				.block();
		context.json(daten);
	}

	private void getMeineHelden(Context context) {
		Long id = DgoSecurity.getAuthenticatedUser().getId();
		getHeldenForUser(context, id);
	}

	private void updatePublic(Context context) {
		Long held = Long.valueOf(context.pathParam("held"));
		boolean value = Boolean.valueOf(context.pathParam("public"));
		HeldEntity heldEntity = HeldRepositoryService.findById(held);
		HeldRepositoryService.updatePublic(heldEntity, value);
	}

	private void updateActive(Context context) {
		Long held = Long.valueOf(context.pathParam("held"));
		boolean value = Boolean.valueOf(context.pathParam("active"));
		HeldEntity heldEntity = HeldRepositoryService.findById(held);
		HeldRepositoryService.updateActive(heldEntity, value);
	}



	private void getHeldenForUser(Context context) {

		long user = Long.valueOf(context.pathParam("user"));
		getHeldenForUser(context, user);
	}

	private void getHeldenForUser(Context context, Long id) {

		Map<Long, GruppeEntity> map = new HashMap<>();
		List<HeldDto> helden = HeldRepositoryService.findByUserId(id)
				.stream()
				.map(heldEntity -> {
					if(!map.containsKey(heldEntity.getGruppe())) {
						map.put(heldEntity.getGruppe(), GruppeRepository.findById(heldEntity.getGruppe()));
					}

					String name = map.get(heldEntity.getGruppe()).getName();
					return new HeldDto(heldEntity, name);
				})
				.collect(Collectors.toList());
		context.json(helden);
	}
}
