package de.failender.dgo.persistance.gruppe;

public class GruppeRepositoryService {

	public static GruppeEntity findByName(String name) {
		return GruppeRepository.findByName(name);
	}

	public static boolean existsByName(String name) {
		return GruppeRepository.existsByName(name);
	}

	public static void save(GruppeEntity gruppeEntity) {
		GruppeRepository.save(gruppeEntity);
	}
}
