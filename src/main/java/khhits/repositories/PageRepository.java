package khhits.repositories;
import khhits.models.Page;
import org.springframework.stereotype.Repository;

/**
 * Created by saksonita1 on 7/25/17.
 */
@Repository
public interface PageRepository {
    public int save(Page page);

}
