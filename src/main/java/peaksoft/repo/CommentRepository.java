package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Comment;
import peaksoft.entity.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select p.comments from Post p where p.id = :postId")
    List<Comment> findAllCommentByPostId(Long postId);
//    @Query("select u from User u join Comment.likes l on l.user.id = u.id where l.comment.id = :commentId ")
//    List<User> getLikesByCommentId(Long commentId);
}
