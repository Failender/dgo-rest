package de.failender.ezql.book;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

public class BookRepository extends EzqlRepository<BookEntity> {
    @Override
    protected EntityMapper<BookEntity> getMapper() {
        return BookMapper.INSTANCE;
    }


}
