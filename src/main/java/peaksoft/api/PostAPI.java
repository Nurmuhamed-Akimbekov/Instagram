package peaksoft.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Comment;
import peaksoft.entity.Image;
import peaksoft.entity.Post;
import peaksoft.service.CommentService;
import peaksoft.service.LikeService;
import peaksoft.service.PostService;
import peaksoft.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostAPI {
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;


    @GetMapping("/newPost/{userId}")
    public String createPost(@PathVariable Long userId, Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("userId", userId);
        return "new-post";
    }

    @PostMapping("/savePost/{userId}")
    public String savePost(@ModelAttribute("post") Post post,
                           @PathVariable Long userId,
                           HttpServletRequest request) {
        String[] imageURLs = request.getParameterValues("additionalImageUrls");

        if (imageURLs != null) {
            for (String imageURL : imageURLs) {
                Image image = new Image();
                image.setImageURL(imageURL);
                post.getImages().add(image);
            }
        }
        postService.save(userId, post);
        return "redirect:/register/profUser/" + userId;
    }


    @GetMapping("/viewComment/{userId}/{postId}")
    public String viewComment(Model model, @PathVariable Long postId,
                              @PathVariable Long userId) {
        Post findPost = postService.findPostById(postId);
        model.addAttribute("foundPost", findPost);
        model.addAttribute("postId", postId);
        model.addAttribute("userId", userId);
        model.addAttribute("newComment", new Comment());
        return "comment";
    }

    @GetMapping("/comLike/{userId}/{postId}/{comId}")
    public String commentLike(@PathVariable Long userId,
                              @PathVariable Long postId,
                              @PathVariable Long comId) {
        likeService.likeComment(userId, comId);
        return "redirect:/post/viewComment/" + userId + "/" + postId;
    }

    @PostMapping("/savedComment/{userId}/{postId}")
    public String savedComment(@PathVariable Long postId,
                               @PathVariable Long userId,
                               @ModelAttribute("newComment") Comment comment,
                               Model model) {
        model.addAttribute("postId", postId);
        commentService.saveComment(userId, postId, comment);
        return "redirect:/post/viewComment/" + userId + "/" + postId;
    }

    @GetMapping("/editPost/{userId}/{postId}")
    public String editPost(@PathVariable Long postId,
                           @PathVariable Long userId, Model model) {
        Post foundPost = postService.findPostById(postId);
        model.addAttribute("postGetId", postId);
        model.addAttribute("userId", userId);
        model.addAttribute("editPost", foundPost);
        return "edit-post";
    }

    @PostMapping("/savePostAfter/{userId}/{postId}")
    public String savePostAfterEdit(@PathVariable Long postId,
                                    @PathVariable Long userId,
                                    @ModelAttribute("editPost") Post post) {
        postService.update(postId, post);
        return "redirect:/register/profUser/" + userId;
    }

    @GetMapping("/deletePost/{userId}/{postId}")
    public String deletePost(@PathVariable Long postId,
                             @PathVariable Long userId) {
        postService.delete(postId);
        return "redirect:/register/profUser/" + userId;
    }

    @GetMapping("/deleteComment/{userId}/{postId}/{comId}")
    public String deleteComment(@PathVariable Long comId,
                                @PathVariable Long userId,
                                @PathVariable Long postId) {
        commentService.deleteComment(comId);
        return "redirect:/post/viewComment/" + userId + "/" + postId;
    }

    @GetMapping("/likes/{userId}/{postId}")
    public String isLike(@PathVariable Long postId,
                         @PathVariable Long userId) {
        likeService.likePost(userId, postId);
        return "redirect:/register/main/" + userId;
    }

}
