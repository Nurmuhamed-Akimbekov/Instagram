package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import peaksoft.entity.Comment;
import peaksoft.entity.Like;
import peaksoft.entity.Post;
import peaksoft.entity.User;
import peaksoft.exception.NotFoundexception;
import peaksoft.repo.CommentRepository;
import peaksoft.repo.PostRepository;
import peaksoft.repo.UserRepository;
import peaksoft.service.CommentService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepo;
    private final CommentRepository commentRepo;
    private final UserRepository userRepo;
    @Override
    public void saveComment(Long userId, Long postId, Comment comment) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getComments().add(comment);
        post.getComments().add(comment);
        comment.setPost(post);
        comment.setUser(user);
        commentRepo.save(comment);
    }

    @Override
    public void deleteComment( Long commentId) {
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<Comment> findAllCommentByPostId(Long postId) {
        return commentRepo.findAllCommentByPostId(postId);
    }

    @Override
    public List<User> getLikesByCommentId(Long commentId) {
//        return commentRepo.getLikesByCommentId(commentId);
        return null;
    }

}
