package de.failender.dgo.persistance.pdf;

import de.failender.ezql.EzqlConnector;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class PdfRepository {

	public static void save(PdfEntity pdfEntity) {
		new InsertQuery<>(PdfMapper.INSTANCE, pdfEntity).execute();
	}

	public static PdfEntity findByName(String name) {

		return EntityMapper.firstOrNull(SelectQuery.Builder.selectAll(PdfMapper.INSTANCE)
				.where(PdfMapper.NAME, name)
				.limit(1)
				.build()
				.execute());

	}

	public static boolean canUserView(Long pdf, Long userId) {
		String sql = "SELECT EXISTS(SELECT 1 FROM PDFS_TO_USER WHERE PDF_ID = " + pdf + " AND USER_ID = " + userId + ");";
		try (Statement statement = EzqlConnector.getConnection().createStatement()) {
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			return rs.getBoolean("exists");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
