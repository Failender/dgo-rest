package de.failender.dgo.persistance.gruppe;

import java.util.List;

public class GruppeRepositoryService {

	public static GruppeEntity findByName(String name) {
		return GruppeRepository.findByName(name);
	}

	public static boolean existsByName(String name) {
		return GruppeRepository.existsByName(name);
	}

	public static GruppeEntity findById(Long id) {
		return GruppeRepository.findById(id);
	}

	public static void save(GruppeEntity gruppeEntity) {
		GruppeRepository.save(gruppeEntity);
	}

	public static List<GruppeEntity> findAll() {
		return GruppeRepository.findAll();
	}
}
