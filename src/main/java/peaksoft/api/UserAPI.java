package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Post;
import peaksoft.entity.User;
import peaksoft.entity.UserInfo;
import peaksoft.service.FollowerService;
import peaksoft.service.PostService;
import peaksoft.service.UserInfoService;
import peaksoft.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class UserAPI {
    private final UserService userService;
    private final UserInfoService userInfoService;
    private final PostService postService;
    private final FollowerService followerService;

    @GetMapping()
    public String loginModel(Model model) {
        User user = new User();
        model.addAttribute("currentUser", user);
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("currentUser") User user, Model model) {
        try {
            User currentUser = userService.signIn(user.getUserName(), user.getPassword());
            return checkingSome(model, currentUser.getId());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Incorrect userName or password, Please white correct");
            return "/error-page";
        }
    }

    @GetMapping("/createUser")
    public String create(Model model) {
        model.addAttribute("newUser", new User());
        return "/sign-up";
    }

    @PostMapping("/saveUser")
    public String signUp(@ModelAttribute("newUser") User user, Model model) {
        try {
            User currentUser = userService.save(user);
            return checkingSome(model, currentUser.getId());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Duplicate email or username. Please choose another one.");
            return "error-page";
        }
    }

    @GetMapping("/main/{userId}")
    private String checkingSome(Model model, @PathVariable Long userId) {
        List<User> users = userService.getSubscriptionByUserId(userId);
        List<Post> allPosts = postService.getAllPost();
        model.addAttribute("allPosts", allPosts);
        model.addAttribute("userId", userId);
        model.addAttribute("subscriptions", users);
        return "home-page";
    }


    @GetMapping("/delUserWIthPass/{userId}")
    public String passwordForDelete(@PathVariable Long userId,
                                    Model model) {
        model.addAttribute("currentUser", userId);
        return "deleteUser";
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUserById(@PathVariable Long userId,
                                 @RequestParam String password,
                                 Model model) {
        try {
            userService.delete(userId, password);
            return "redirect:/register";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Incorrect password, Please white correct");
            return "error-page";
        }
    }

    @GetMapping("/profUser/{userId}")
    public String profilePage(Model model, @PathVariable Long userId) {
        try {
            User cerruntUser = userService.findUserById(userId);
            Long id = cerruntUser.getId();
            int subscribers = followerService.getNumberOfSubscribers(id);
            int subscriptions = followerService.getNumberOfSubscriptions(id);
            List<Post> posts = new ArrayList<>(cerruntUser.getPosts());
            Collections.reverse(posts);
            model.addAttribute("subscribers", subscribers);
            model.addAttribute("posts", posts);
            model.addAttribute("subscriptions", subscriptions);
            model.addAttribute("currentUser", cerruntUser);
            model.addAttribute("userId", id);
            return "profile";
        } catch (Exception e) {
            return "error-page";
        }
    }


    @GetMapping("/editProf/{userId}")
    public String editUser(Model model, @PathVariable Long userId) {
        try {
            User user = userService.findUserById(userId);
            UserInfo currentUserInfo = userInfoService.findUserInfoByUserId(userId);
            model.addAttribute("current", user);
            model.addAttribute("currentUserInfo", currentUserInfo);
            model.addAttribute("userId", userId);
            return "edit-profile";
        } catch (Exception e) {
            return "error-page";
        }
    }

    @PostMapping("/saveProfiles/{userId}")
    public String saveProfile(@ModelAttribute("current") User currentUser,
                              @ModelAttribute("currentUserInfo") UserInfo userInfo,
                              Model model,
                              @PathVariable Long userId) {
        try {
            userService.update(userId, currentUser);
            userInfoService.updateUserInfo(userInfo.getId(), userInfo);
            return "redirect:/register/profUser/" + userId;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Duplicate email or username. Please choose another one.");
            return "error-page";
        }
    }
}
