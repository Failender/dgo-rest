package de.failender.ezql.clause;

import de.failender.ezql.EzqlTest;
import de.failender.ezql.user.UserMapper;
import org.junit.Assert;
import org.junit.Test;

public class UpdateQueryTest extends EzqlTest {


	@Test
	public void testUpdate() {
		UserMapper.updateName(0L, "NEW_NAME");
		Assert.assertEquals(UserMapper.selectOnlyName(0L).getName(), "NEW_NAME");
	}
}
