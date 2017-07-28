package khhits.repositories;
import khhits.models.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by saksonita1 on 7/25/17.
 */
@Repository
public interface PageRepository extends CrudRepository<Page, Long> {
    List<Page> findByPageTitle( String pageTitle );
}
