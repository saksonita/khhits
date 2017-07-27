package khhits.repositories;
import org.springframework.stereotype.Repository;
import khhits.models.Page;
/**
 * Created by saksonita1 on 7/25/17.
 */
@Repository
public interface PageRepository {
    public int save(Page page);
}
