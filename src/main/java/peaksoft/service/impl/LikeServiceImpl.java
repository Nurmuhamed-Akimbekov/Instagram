package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Comment;
import peaksoft.entity.Like;
import peaksoft.entity.Post;
import peaksoft.entity.User;
import peaksoft.repo.CommentRepository;
import peaksoft.repo.LikeRepository;
import peaksoft.repo.PostRepository;
import peaksoft.repo.UserRepository;
import peaksoft.service.LikeService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepo;
    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final CommentRepository commentRepo;

    @Override @Transactional
    public void likePost(Long userId, Long postId) {
        Like like = new Like();
        Post post = postRepo.findById(postId).orElseThrow(null);
        User user = userRepo.findById(userId).orElseThrow(null);

        boolean islike = true;
        for (Like like1: post.getLikes()){
            if(like1.getUser().getId().equals(userId)){
                post.getLikes().remove(like1);
                likeRepo.delete(like1);
                islike = false;
                break;
            }
        }
        if (islike){like.setUser(user);
            post.getLikes().add(like);
            like.setPost(post);
            likeRepo.save(like);
        }
    }

    @Override
    @Transactional
    public void likeComment(Long userId, Long commentId) {
        Like like = new Like();
        Comment comment = commentRepo.findById(commentId).orElseThrow(null);
        User user = userRepo.findById(userId).orElseThrow(null);

        boolean islike = true;
        for (Like like1: comment.getLikes()){
            if(like1.getUser().getId().equals(userId)){
                comment.getLikes().remove(like1);
                likeRepo.delete(like1);
                islike = false;
                break;
            }
        }
        if (islike){like.setUser(user);
            comment.getLikes().add(like);
            like.setComment(comment);
            likeRepo.save(like);
        }
    }

}
