package peaksoft.service;

import peaksoft.entity.Post;

import java.util.List;

public interface PostService {
    Post save(Long userId,Post newPost);
    Post update(Long postId,Post newPost);
    void delete (Long postId);
    Post findPostById(Long postId);
    List<Post> getAllPost();
    List<Post> getAllPostById(Long userId);
    List<Post> findAllPostByFollowers(List<Long> followersId);
}
