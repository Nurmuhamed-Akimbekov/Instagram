package peaksoft.service;

import peaksoft.entity.Comment;
import peaksoft.entity.User;

import java.util.List;

public interface CommentService {
    void saveComment(Long userId, Long postId, Comment comment);

    void deleteComment(Long commentId);
    List<Comment> findAllCommentByPostId(Long postId);

    List<User> getLikesByCommentId(Long commentId);
}
