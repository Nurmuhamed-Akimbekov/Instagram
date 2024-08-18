package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Post;
import peaksoft.entity.User;
import peaksoft.service.FollowerService;
import peaksoft.service.UserService;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("follower")
public class FollowerAPI {
    private final UserService userService;
    private final FollowerService followerService;


    @GetMapping("/subscribers/{userId}")
    public String subscribers(@PathVariable Long userId, Model model) {
        List<User> users = userService.getSubscribersByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("users", users);
        return "subscribers";
    }

    @GetMapping("/subscriptions/{userId}")
    public String subscriptions(@PathVariable Long userId, Model model) {
        List<User> users = userService.getSubscriptionByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("users", users);
        return "subscriptions";
    }

    @GetMapping("/CurrentUser/{userId}/{subId}")
    public String someUser(@PathVariable Long userId,
                           @PathVariable Long subId, Model model) {
        try {
            User otherUser = userService.findOtherUserById(userId, subId);
            List<Post> posts = otherUser.getPosts();
            Collections.reverse(posts);
            int subscriptions = followerService.getNumberOfSubscriptions(subId);
            int subscribers = followerService.getNumberOfSubscribers(subId);
            model.addAttribute("posts", posts);
            model.addAttribute("subscriptions", subscriptions);
            model.addAttribute("subscribers", subscribers);
            model.addAttribute("currentUser", otherUser);
            model.addAttribute("userId", userId);
            return "other";
        } catch (Exception e) {
            return "redirect:/register/profUser/" + userId;
        }
    }

    @GetMapping("/CurrentUserSubscriptions/{userId}/{otherUserId}")
    public String otherUserSubscriptions(@PathVariable Long userId,
                                         @PathVariable Long otherUserId, Model model) {
        List<User> users = userService.getSubscriptionByUserId(otherUserId);
        model.addAttribute("users", users);
        model.addAttribute("userId", userId);
        return "subscriptions";
    }

    @GetMapping("/CurrentUserSubscribers/{userId}/{otherUserId}")
    public String otherUserSubscribers(@PathVariable Long userId,
                                       @PathVariable Long otherUserId, Model model) {
        List<User> users = userService.getSubscribersByUserId(otherUserId);
        model.addAttribute("users", users);
        model.addAttribute("userId", userId);
        return "subscribers";
    }
}
