package peaksoft.service;

import peaksoft.entity.User;
import peaksoft.exception.NotFoundexception;

import java.util.List;

public interface UserService {

    User save(User newUser);
    User update(Long userId,User newUser) throws NotFoundexception;
    void delete(Long userId, String password) throws NotFoundexception;
    User findUserById(Long userId) throws NotFoundexception;
    User signIn(String userName,String password);
    List<User> searchUsers(String keyword);
    List<User> getSubscriptionByUserId(Long userId);
    List<User> getSubscribersByUserId(Long userId);

    public User findOtherUserById(Long userId, Long subId);

}
