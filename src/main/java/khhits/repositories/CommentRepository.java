package khhits.repositories;


import khhits.models.Comment;
import org.springframework.stereotype.Repository;

/**
 * Created by HP1 on 5/3/2017.
 */
@Repository
public interface CommentRepository {

    public int save( Comment comment );
}
