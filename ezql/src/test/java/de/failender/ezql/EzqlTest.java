package de.failender.ezql;

import de.failender.ezql.user.UserEntity;
import de.failender.ezql.user.UserMapper;
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
		EzqlConnector.connect(postgres.getDriverClassName(), postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
		String sql = IOUtils.toString(EzqlTest.class.getResourceAsStream("/setup.sql"), "UTF-8");
		EzqlConnector.execute(sql);
	}
}
