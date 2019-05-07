package de.failender.ezql.clause;


import de.failender.ezql.EzqlTest;
import de.failender.ezql.user.UserEntity;
import de.failender.ezql.user.UserMapper;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SelectQueryTest extends EzqlTest {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void testSelect() {


		List<UserEntity> userEntityList = UserMapper.selectAll();
		Assert.assertEquals(userEntityList.size(), 3);

		UserEntity userEntity = UserMapper.selectById(0);
		Assert.assertEquals(userEntity.getName(), "Demo");
		Assert.assertEquals(userEntity.getPassword(), "1234");
		Assert.assertEquals((Object)userEntity.getId(), 0);
		Assert.assertEquals(userEntity.isActive(), true);


	}

	@Test
	public void testSelectSpecificFields() {


		UserEntity userEntity = UserMapper.selectOnlyName(0);
		Assert.assertNull(userEntity.getPassword());
		Assert.assertNull(userEntity.getId());
		Assert.assertEquals(userEntity.getName(), "Demo");
	}

	@Test
	public void testDate() throws ParseException {
		//UserEntity userEntity = UserMapper.selectById(0);
		//Assert.assertEquals(userEntity.getLastLogin(), sdf.parse("2011-01-01"));
	}
}
