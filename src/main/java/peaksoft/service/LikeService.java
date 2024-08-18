package peaksoft.service;

import peaksoft.entity.User;

import java.util.List;

public interface LikeService {
    void likePost(Long userId, Long postId);
    void likeComment(Long userId,Long commentId);
//   List<User> getLikesByPostId(Long postid);
//   List<User> getLikesByCommentId(Long commentId);
}
