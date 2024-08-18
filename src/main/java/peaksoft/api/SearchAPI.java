package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Post;
import peaksoft.entity.User;
import peaksoft.service.FollowerService;
import peaksoft.service.LikeService;
import peaksoft.service.PostService;
import peaksoft.service.UserService;

import java.util.ArrayList;
import java.util.Collections;


import java.util.List;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchAPI {

    private final UserService userService;
    private final FollowerService followerService;


    @GetMapping("/createSearch/{userId}")
    public String createSearch(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "search";
    }


    @PostMapping("/Search/{userId}")
    public String pageTOSearch(@PathVariable Long userId,
                               @RequestParam String keyword, Model model) {
        List<User> findUsers = userService.searchUsers(keyword);
        model.addAttribute("users", findUsers);
        model.addAttribute("userId", userId);
        return "search";
    }


    @GetMapping("/otherUser/{userId}/{otherUserId}")
    public String otherUser(@PathVariable Long userId,
                            @PathVariable Long otherUserId,
                            Model model) {
        try {
            User foundUser = userService.findUserById(otherUserId);
            User currentUser = userService.findUserById(userId);

            if (foundUser.getUserName().equalsIgnoreCase(currentUser.getUserName())) {
                return "redirect:/register/profUser/" + userId;
            } else {
                List<Post> postList = foundUser.getPosts();
                List<Post> posts = new ArrayList<>(postList);
                Collections.reverse(posts);
                int subscribers = followerService.getNumberOfSubscribers(otherUserId);
                int subscriptions = followerService.getNumberOfSubscriptions(otherUserId);
                model.addAttribute("userId", userId);
                model.addAttribute("currentUser", foundUser);
                model.addAttribute("subscribers", subscribers);
                model.addAttribute("subscriptions", subscriptions);
                model.addAttribute("posts", posts);
                return "otherUser";
            }
        } catch (Exception e) {
            return "/error-page";
        }
    }


    @GetMapping("/addSubscriber/{userId}/{otherUserId}")
    public String addSubscriber(@PathVariable Long userId,
                                @PathVariable Long otherUserId) {
        followerService.following(userId, otherUserId);
        return "redirect:/search/otherUser/" + userId + "/" + otherUserId;
    }


}



