package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Follower;
import peaksoft.entity.User;
import peaksoft.repo.FollowerRepository;
import peaksoft.repo.UserRepository;
import peaksoft.service.FollowerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    @Override
    public int getNumberOfSubscribers(Long userId) {
        Follower follower = followerRepository.findFollowerByUserId(userId);
        return follower.getSubscribers().size();
    }
    @Override
    public int getNumberOfSubscriptions(Long userId) {
        Follower follower = followerRepository.findFollowerByUserId(userId);
        return follower.getSubscriptions().size();
    }

    @Override @Transactional
    public void following(Long currentUserId, Long subscriberId) {
        User currentUser = userRepository.findById(currentUserId).orElseThrow(null);
        User subscriberUser = userRepository.findById(subscriberId).orElseThrow(null);

        Follower currentUserFollower = currentUser.getFollower();
        Follower subscriberFollower = subscriberUser.getFollower();

        List<Long> currentUserSubscriptions = currentUserFollower.getSubscriptions();
        List<Long> subscriberSubscribers = subscriberFollower.getSubscribers();

        if (currentUserSubscriptions.contains(subscriberId)) {
            currentUserSubscriptions.remove(subscriberId);
            subscriberSubscribers.remove(currentUserId);
        } else {
            currentUserSubscriptions.add(subscriberId);
            subscriberSubscribers.add(currentUserId);
        }
    }

    @Override
    public List<User> getSubscriptionsByPostId(Long postId) {
        Follower followerByUserId = followerRepository.findFollowerByUserId(postId);
        followerByUserId.getSubscriptions();
        return null;
    }

    @Override
    public List<User> getSubscribersByPostId(Long postId) {
        return null;
    }

}
