package khhits.repositories;

import khhits.models.Like;
import org.springframework.stereotype.Repository;

/**
 * Created by HP1 on 5/4/2017.
 */
@Repository
public interface LikeRepository {

    public int save( Like like );
}
