package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Like;
import peaksoft.entity.User;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
//    @Query("select l.user from Like l where l.post.id = :postId ")
//    List<User> getLikesByPostId(Long postid);
//    @Query("select l.user from Like l where l.comment.id = :commentId")
//    List<User> getLikesByCommentId(Long commentId);
}
