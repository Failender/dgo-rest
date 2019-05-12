package de.failender.dgo.rest.pdf;

import de.failender.dgo.rest.integration.Beans;
import de.failender.ezql.properties.PropertyReader;
import io.javalin.Context;
import io.javalin.Javalin;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.eclipse.jetty.security.PropertyUserStore;

import java.io.*;
import java.util.List;

public class PdfController {


	private static final String PREFIX = "/api/pdf/";
	private static final String PAGE =PREFIX + ":source/:page";

	public PdfController(Javalin app) {
		app.get(PAGE, this::getPdf);
		try {
			preparePdf("lcd");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getPdf(Context context) {

		String source = context.pathParam("source");
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
		System.out.println("Done preparing pdf " + source);

	}

	private File pdfDir(String source) {
		return new File(Beans.HELDEN_API.getCacheHandler().getRoot(), "assetPdfs/" + source);
	}
}
