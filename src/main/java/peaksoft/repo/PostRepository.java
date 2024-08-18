package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Post;
import peaksoft.entity.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select u.posts from User u where u.id = :userId ")
    List<Post> findAllPostByUserId(Long userId);
    @Query("select u.posts from User u where u.id in (:followersId) ")
    List<Post> findAllPostByFollowerId(List<Long> followersId);
//    @Query("select p. from Post p where p.id = :postId")
//    List<User> getLikes(Long postId);

}
