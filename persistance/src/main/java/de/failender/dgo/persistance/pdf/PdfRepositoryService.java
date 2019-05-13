package de.failender.dgo.persistance.pdf;

import de.failender.dgo.persistance.user.UserEntity;

public class PdfRepositoryService {

	public static void save(PdfEntity pdfEntity) {
		PdfRepository.save(pdfEntity);
	}

	public static boolean canUserView(String pdf, UserEntity userEntity) {
		PdfEntity pdfEntity = PdfRepository.findByName(pdf);
		if (pdfEntity == null) {
			return false;
		}
		return PdfRepository.canUserView(pdfEntity.getId(), userEntity.getId());
	}
}
