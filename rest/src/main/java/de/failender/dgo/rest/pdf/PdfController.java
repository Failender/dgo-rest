package de.failender.dgo.rest.pdf;

import de.failender.dgo.persistance.pdf.PdfEntity;
import de.failender.dgo.persistance.pdf.PdfRepositoryService;
import de.failender.dgo.rest.integration.Beans;
import de.failender.dgo.rest.security.DgoSecurity;
import de.failender.dgo.security.DgoSecurityContext;
import de.failender.dgo.security.NoPermissionException;
import de.failender.ezql.properties.PropertyReader;
import io.javalin.Context;
import io.javalin.Javalin;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class PdfController {


	private static final String PREFIX = "/api/pdf/";
	private static final String PAGE =PREFIX + ":source/:page";
	private static final String VISIBLE =PREFIX + "visible";

	public PdfController(Javalin app) {
		app.get(PAGE, this::getPdf);
		app.get(VISIBLE, this::getVisiblePdfs);
		try {
			preparePdf("lcd");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getVisiblePdfs(Context context) {
		context.json(PdfRepositoryService.getVisiblePdfs(DgoSecurityContext.getAuthenticatedUser()));

	}

	private void getPdf(Context context) {

		String source = context.pathParam("source");
		if (!PdfRepositoryService.canUserView(source, DgoSecurityContext.getAuthenticatedUser())) {
			throw new NoPermissionException();
		}
		int page = Integer.valueOf(context.pathParam("page"));

		try {
			context.result(new FileInputStream(new File(pdfDir(source),(page+1) + ".pdf" )));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void preparePdf(String source) {
		System.out.println("Preparing pdf " + source);
		String propertyName = "helden.online." + source +".path";
		File file = new File(PropertyReader.getProperty(propertyName));
		File outDir = pdfDir(source);
		if(outDir.exists()) {
			System.out.println("skipping preparation, outdir already exists");
			return;
		}
		outDir.mkdirs();
		try {
			PDDocument document = PDDocument.load(file);
			Splitter splitter = new Splitter();
			List<PDDocument> pages = splitter.split(document);
			int i = 1;
			for (PDDocument page : pages) {
				page.save(new File(outDir, i + ".pdf"));
				i++;
				page.close();
			}
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PdfEntity pdfEntity = new PdfEntity();
		pdfEntity.setName(source);
		PdfRepositoryService.save(pdfEntity);
		System.out.println("Done preparing pdf " + source);

	}

	private File pdfDir(String source) {
		return new File(Beans.HELDEN_API.getCacheHandler().getRoot(), "assetPdfs/" + source);
	}
}
