package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundexception;
import peaksoft.entity.Follower;
import peaksoft.entity.User;
import peaksoft.entity.UserInfo;
import peaksoft.repo.UserRepository;
import peaksoft.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    @Override
    public User save(User newUser) {
        Follower follower = new Follower();
        UserInfo userInfo = new UserInfo();
        follower.setSubscriptions(new ArrayList<>());
        follower.setSubscribers(new ArrayList<>());
        newUser.setFollower(follower);
        newUser.setUserInfo(userInfo);
       return userRepo.save(newUser);
    }

    @Override @Transactional
    public User update(Long userId, User newUser) throws NotFoundexception {
       User user = userRepo.findById(userId).orElseThrow(()
               -> new NotFoundexception("User with id: " + userId + " not found!"));
       user.setUserName(newUser.getUserName());
       user.setPassword(newUser.getPassword());
       user.setEmail(newUser.getEmail());
       user.setPhoneNumber(newUser.getPhoneNumber());
        return user;
    }

    @Override
    public void delete(Long userId, String password) throws NotFoundexception {
        User foundUser = findUserById(userId);
        if (foundUser.getPassword().equals(password)){
            userRepo.deleteById(userId);
        }else throw new NotFoundexception("User not found");
    }

    @Override
    public User findUserById(Long userId)  {
        return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User signIn(String userName, String password) {
        return userRepo.findUserByNameAndPassword(userName,password);
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepo.searchUsers("%" + keyword + "%");
    }

    @Override
    public List<User> getSubscriptionByUserId(Long userId) {
        User foundUser = findUserById(userId);
        List<Long> subscriptions = foundUser.getFollower().getSubscriptions();
        return userRepo.subscriptionsOfUser(subscriptions);
    }

    @Override
    public List<User> getSubscribersByUserId(Long userId) {
        User foundUser = findUserById(userId);
        List<Long> subscribers = foundUser.getFollower().getSubscribers();
        return userRepo.subscriptionsOfUser(subscribers);
    }

    @Override
    public User findOtherUserById(Long userId, Long subId) throws RuntimeException {
        User currentUser = findUserById(userId);
        User foundUser = findUserById(subId);
        if (!foundUser.getUserName().equalsIgnoreCase(currentUser.getUserName())){
            return foundUser;
        }
        throw new RuntimeException();
    }

}
