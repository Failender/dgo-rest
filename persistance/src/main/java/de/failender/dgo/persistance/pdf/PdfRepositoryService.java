package de.failender.dgo.persistance.pdf;

import de.failender.dgo.persistance.user.UserEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

	public static List<String> getVisiblePdfs(UserEntity userEntity) {
		List<Long> pdfIds = PdfRepository.findVisiblePdfs(userEntity.getId());
		List<String> pdfs = PdfRepository.findByIdIn(pdfIds)
				.stream()
				.map(PdfEntity::getName)
				.collect(Collectors.toList());
		return pdfs;
	}
}
