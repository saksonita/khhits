package khhits.repositories;

import org.springframework.stereotype.Repository;
import khhits.models.Post;

/**
 * Created by HP1 on 5/3/2017.
 */
@Repository
public interface PostRepository {

    public int save( Post post );

    public int count( String postId );
}
