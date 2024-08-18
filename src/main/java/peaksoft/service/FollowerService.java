package peaksoft.service;

import peaksoft.entity.Follower;
import peaksoft.entity.User;

import java.util.List;

public interface FollowerService {
    int getNumberOfSubscribers(Long userId);

    int getNumberOfSubscriptions(Long userId);
    void following(Long currentUserId, Long subscriberId);

    List<User> getSubscriptionsByPostId(Long postId);
    List<User> getSubscribersByPostId(Long postId);

}
