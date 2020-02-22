package de.failender.ezql.clause;

import de.failender.ezql.EzqlTest;
import de.failender.ezql.book.BookEntity;
import de.failender.ezql.book.BookMapper;
import de.failender.ezql.queries.join.JoinQuery;
import de.failender.ezql.queries.join.JoinType;
import de.failender.ezql.user.UserEntity;
import de.failender.ezql.user.UserMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JoinQueryTest extends EzqlTest {

    @Test
    public void joinQueryTest() {

        /*
        List<JoinResult> resultList = JoinQuery.Builder.create(UserMapper.INSTANCE, JoinResult::new)
                .where(UserMapper.INSTANCE, UserMapper.ID, 1L)
                .returns(BookMapper.INSTANCE, (result, entity) -> result.setBook(entity), BookMapper.FIELDS)
                .join(JoinType.INNER, UserMapper.INSTANCE, UserMapper.ID, BookMapper.INSTANCE, BookMapper.USER)
                .execute();
        Assert.assertEquals(resultList.size(), 3);
        for (JoinResult result : resultList) {
            Assert.assertNull(result.getUserEntity());
        }
        Assert.assertEquals(resultList.get(0).getBook().getName(), "Alpha");
        Assert.assertEquals(resultList.get(1).getBook().getName(), "Beta");
        Assert.assertEquals(resultList.get(2).getBook().getName(), "Gamma");


         */
        List<JoinResult> resultList = JoinQuery.Builder.create(UserMapper.INSTANCE, JoinResult::new)
                .where(UserMapper.INSTANCE, UserMapper.ID, 2L)
                .returns(BookMapper.INSTANCE, (result, entity) -> result.setBook(entity), BookMapper.FIELDS)
                .returns(UserMapper.INSTANCE, (result, entity) -> result.setUserEntity(entity), UserMapper.FIELDS)
                .join(JoinType.INNER, UserMapper.INSTANCE, UserMapper.ID, BookMapper.INSTANCE, BookMapper.USER)
                .execute();
        Assert.assertEquals(resultList.get(0).getBook().getName(), "Delta");
        Assert.assertEquals(resultList.get(0).getUserEntity().getName(), "User");



    }

    public static class JoinResult {
        private BookEntity book;
        private UserEntity userEntity;

        public BookEntity getBook() {
            return book;
        }

        public void setBook(BookEntity book) {
            this.book = book;
        }

        public UserEntity getUserEntity() {
            return userEntity;
        }

        public void setUserEntity(UserEntity userEntity) {
            this.userEntity = userEntity;
        }
    }
}
