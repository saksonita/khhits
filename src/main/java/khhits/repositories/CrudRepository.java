package khhits.repositories;

import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * Created by saksonita1 on 7/27/17.
 */
public interface CrudRepository<Page, ID extends Serializable> extends Repository<Page, ID> {
    Page findOne( ID primaryKey );
                                                                                                                       (3)

    static default Iterable<Page> findAll() {
        return null;
    }
}
