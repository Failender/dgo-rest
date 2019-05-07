package de.failender.ezql.clause;

import de.failender.ezql.EzqlTest;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.user.UserEntity;
import de.failender.ezql.user.UserMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class InsertClauseTest extends EzqlTest {


    @Test
    public void testInsert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("NAME!");
        userEntity.setActive(false);
        userEntity.setPassword("PASS");

        new InsertQuery<>(UserMapper.INSTANCE, userEntity).execute();
        Assert.assertNotNull(userEntity.getId());

        userEntity = UserMapper.selectById(userEntity.getId());

        Assert.assertEquals(userEntity.getName(), "NAME!");
        Assert.assertEquals(userEntity.getPassword(), "PASS");
        Assert.assertEquals(userEntity.isActive(), false);

    }
}
