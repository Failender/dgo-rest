package de.failender.ezql.book;

import de.failender.ezql.mapper.*;
import de.failender.ezql.mapper.array.IntArrayFieldMapper;
import de.failender.ezql.queries.SelectQuery;
import de.failender.ezql.queries.UpdateQuery;
import de.failender.ezql.user.UserEntity;

import java.util.Arrays;
import java.util.List;

public class BookMapper extends EntityMapper<BookEntity> {


    public static final BookMapper INSTANCE = new BookMapper();

    public static final StringFieldMapper<BookEntity> NAME = new StringFieldMapper<>("NAME",
            BookEntity::setName, BookEntity::getName);

    public static final LongFieldMapper<BookEntity> ID = new LongFieldMapper<>("ID",
            BookEntity::setId, BookEntity::getId);

    public static final LongFieldMapper<BookEntity> USER = new LongFieldMapper<>("USER_ID",
            BookEntity::setUser, BookEntity::getUser);




    @Override
    public String table() {
        return "BOOKS";
    }

    @Override
    public BookEntity create() {
        return new BookEntity();
    }



    public static final List<FieldMapper<BookEntity, ?>> FIELDS = Arrays.asList(ID, NAME, USER);
    @Override
    public List<FieldMapper<BookEntity, ?>> fieldMappers() {
        return FIELDS;
    }

    @Override
    public LongFieldMapper idField() {
        return ID;
    }
}
