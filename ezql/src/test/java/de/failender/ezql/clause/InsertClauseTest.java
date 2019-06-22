package de.failender.ezql.clause;

import de.failender.ezql.EzqlTest;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.user.UserEntity;
import de.failender.ezql.user.UserMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

public class InsertClauseTest extends EzqlTest {


    @Test
    public void testInsert() {
        UUID uuid = UUID.fromString("7dc51234-703e-49b3-8670-b1c468f47f1f");
        UserEntity userEntity = new UserEntity();
        userEntity.setName("NAME!");
        userEntity.setActive(false);
        userEntity.setPassword("PASS");
        userEntity.setUuid(uuid);
        userEntity.setNumbers(Arrays.asList(1, 2, 3, 4));

        new InsertQuery<>(UserMapper.INSTANCE, userEntity).execute();
        Assert.assertNotNull(userEntity.getId());

        userEntity = UserMapper.selectById(userEntity.getId());

        Assert.assertEquals(userEntity.getName(), "NAME!");
        Assert.assertEquals(userEntity.getPassword(), "PASS");
        Assert.assertEquals(userEntity.isActive(), false);
        Assert.assertEquals(userEntity.getNumbers(), Arrays.asList(1, 2, 3, 4));
        Assert.assertEquals(userEntity.getUuid(), uuid);

    }
}
