package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Image;
import peaksoft.entity.Post;
import peaksoft.entity.User;
import peaksoft.repo.PostRepository;
import peaksoft.repo.UserRepository;
import peaksoft.service.PostService;
import peaksoft.service.UserInfoService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    @Override
    public Post save(Long userId, Post newPost) {
        User user = userRepo.findById(userId).orElseThrow(null);
        user.getPosts().add(newPost);
        newPost.setUser(user);
         return postRepo.save(newPost);
    }

    @Override @Transactional
    public Post update(Long postId, Post newPost) {
        Post foundPost = findPostById(postId);
        foundPost.setTitle(newPost.getTitle());
        foundPost.setDescription(newPost.getDescription());
        return foundPost;
    }

    @Override
    public void delete(Long postId) {
        postRepo.deleteById(postId);

    }

    @Override
    public Post findPostById(Long postId) {
        return postRepo.findById(postId).orElseThrow();
    }

    @Override
    public List<Post> getAllPost() {
        return postRepo.findAll();
    }

    @Override
    public List<Post> getAllPostById(Long userId) {
        return postRepo.findAllPostByUserId(userId);
    }

    @Override
    public List<Post> findAllPostByFollowers(List<Long> followersId) {
        return postRepo.findAllPostByFollowerId(followersId);
    }
}
