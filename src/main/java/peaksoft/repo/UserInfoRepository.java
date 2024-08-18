package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    @Query("select u.userInfo from User u where u.id = :userId")
    UserInfo findUserInfoByUserId(Long userId);
}
