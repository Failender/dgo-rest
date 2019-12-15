package de.failender.dgo.rest.helden;

import de.failender.dgo.persistance.gruppe.GruppeEntity;
import de.failender.dgo.persistance.gruppe.GruppeRepositoryService;
import de.failender.dgo.persistance.held.*;
import de.failender.dgo.rest.integration.Beans;
import de.failender.dgo.rest.security.DgoSecurity;
import de.failender.dgo.security.DgoSecurityContext;
import de.failender.heldensoftware.api.requests.ReturnHeldDatenWithEreignisseRequest;
import de.failender.heldensoftware.api.requests.ReturnHeldPdfRequest;
import de.failender.heldensoftware.xml.datenxml.Daten;
import io.javalin.Context;
import io.javalin.Javalin;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HeldenController {

	public static final String PREFIX = "api/helden/";
	public static final String FOR_USER = PREFIX + "user/:user";
	public static final String MEINE_HELDEN = PREFIX + "meine";
	public static final String HELD = PREFIX + "held/:held/:version/daten";
	public static final String PDF = PREFIX + "held/:held/:version/pdf";
	public static final String UPDATE_PUBLIC = PREFIX + "held/:held/public/:public";
	public static final String UPDATE_ACTIVE = PREFIX + "held/:held/active/:active";

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public HeldenController(Javalin javalin) {

		javalin.get(FOR_USER, this::getHeldenForUser);
		javalin.get(MEINE_HELDEN, this::getMeineHelden);
		javalin.get(HELD, this::getHeld);
		javalin.get(PDF, this::getPdf);
		javalin.put(UPDATE_ACTIVE, this::updateActive);
		javalin.put(UPDATE_PUBLIC, this::updatePublic);
	}

	private void getHeld(Context context) {
		Long held = Long.valueOf(context.pathParam("held"));
		int version = Integer.valueOf(context.pathParam("version"));
		HeldEntity heldEntity = HeldRepositoryService.findById(held);
		VersionEntity versionEntity = VersionRepositoryService.findVersion(heldEntity, version);
		Daten daten = Beans.HELDEN_API.request(new ReturnHeldDatenWithEreignisseRequest(heldEntity.getId(), null, versionEntity.getCacheId()))
				.block();
		context.json(daten);
	}

	private void getPdf(Context context) {

		Long held = Long.valueOf(context.pathParam("held"));
		int version = Integer.valueOf(context.pathParam("version"));
		HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(held);
		VersionEntity versionEntity = VersionRepositoryService.findVersion(heldEntity, version);

		context.result(
			Beans.HELDEN_API.provideDownload(new ReturnHeldPdfRequest(held, null, versionEntity.getCacheId())));
	}

	private void getMeineHelden(Context context) {
		Long id = DgoSecurityContext.getAuthenticatedUser().getId();
		getHeldenForUser(context, id);
	}

	private void updatePublic(Context context) {
		Long held = Long.valueOf(context.pathParam("held"));
		boolean value = Boolean.valueOf(context.pathParam("public"));
		HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(held);
		HeldRepositoryService.updatePublic(heldEntity, value);
	}

	private void updateActive(Context context) {
		Long held = Long.valueOf(context.pathParam("held"));
		HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(held);
		boolean value = Boolean.valueOf(context.pathParam("active"));
		HeldRepositoryService.updateActive(heldEntity, value);
	}



	private void getHeldenForUser(Context context) {

		long user = Long.valueOf(context.pathParam("user"));
		getHeldenForUser(context, user);
	}

	private void getHeldenForUser(Context context, Long id) {

		context.json(map(HeldRepositoryService.findByUserId(id)));
	}

	public static List<HeldDto> map(List<HeldEntity> heldEntities) {
		Map<Long, GruppeEntity> map = new HashMap<>();
		return heldEntities
				.stream()
				.map(heldEntity -> {
					if(!map.containsKey(heldEntity.getGruppe())) {
						map.put(heldEntity.getGruppe(), GruppeRepositoryService.findById(heldEntity.getGruppe()));
					}

					VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(heldEntity);
					String name = map.get(heldEntity.getGruppe()).getName();
					return new HeldDto(heldEntity, name, FORMATTER.format(versionEntity.getCreatedDate()), versionEntity.getVersion());
				})
				.collect(Collectors.toList());
	}
}
