package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(""" 
            select u from User u
                        where u.userName ilike (:keyword)
                        or u.userInfo.fullName ilike (:keyword)
                        """)
    List<User> searchUsers(String keyword);

    @Query("select u from User u where u.userName =:userName and u.password =:password")
    User findUserByNameAndPassword(String userName, String password);

    @Query("select u from User u where u.id in :subscriptions")
    List<User> subscriptionsOfUser(List<Long> subscriptions);

    @Query("select u from User u where u.id in :subscribers")
    List<User> subscribersOfUser(List<Long> subscribers);



}
