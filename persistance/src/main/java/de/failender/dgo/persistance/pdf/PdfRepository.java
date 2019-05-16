package de.failender.dgo.persistance.pdf;

import de.failender.ezql.EzqlConnector;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public static List<Long> findVisiblePdfs(Long user) {
		String sql = "SELECT PDF_ID FROM PDFS_TO_USER WHERE USER_ID = " +user;
		try(Statement statement = EzqlConnector.getConnection().createStatement()) {
			ResultSet rs = statement.executeQuery(sql);
			List<Long> pdfIds = new ArrayList<>();
			while(rs.next()) {
				pdfIds.add(rs.getLong("PDF_ID"));
			}
			return pdfIds;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<PdfEntity> findByIdIn(List<Long> ids) {
		if(ids.isEmpty()) {
			return Collections.emptyList();
		}
		return SelectQuery.Builder.selectAll(PdfMapper.INSTANCE)
				.whereIn(PdfMapper.ID, ids)
				.build()
				.execute();
	}
}
