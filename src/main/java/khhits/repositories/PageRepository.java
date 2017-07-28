package khhits.repositories;
import khhits.models.Page;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by saksonita1 on 7/25/17.
 */
@Repository
public interface PageRepository extends CrudRepository<Page, Long> {
    List<Page> findByTitle( String title );
}
