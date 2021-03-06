package de.failender.ezql;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;

public class EzqlTest {

	@Rule
	public PostgreSQLContainer postgres = new PostgreSQLContainer();

	@Before
	public void setup() throws IOException {
		EzqlConnector.initialize(postgres.getDriverClassName(), postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
		EzqlConnector.allocateConnection();
		String sql = IOUtils.toString(EzqlTest.class.getResourceAsStream("/setup.sql"), "UTF-8");
		EzqlConnector.execute(sql);
	}

	@After
	public void after() {
		EzqlConnector.releaseConnection();
	}
}
