package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.UserInfo;
import peaksoft.exception.NotFoundexception;
import peaksoft.repo.UserInfoRepository;
import peaksoft.service.UserInfoService;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepo;
    @Override
    public UserInfo findUserInfoByUserId(Long userId) throws NotFoundexception {
        UserInfo userInfo = userInfoRepo.findUserInfoByUserId(userId);
        if(userInfo != null) return userInfo;
        else throw new NotFoundexception("User not found");
    }

    @Override @Transactional
    public UserInfo updateUserInfo(Long userinfoId, UserInfo newUserInfo) {
        UserInfo userInfo = userInfoRepo.findById(userinfoId).get();
        userInfo.setFullName(newUserInfo.getFullName());
        userInfo.setBiography(newUserInfo.getBiography());
        userInfo.setGender(newUserInfo.getGender());
        userInfo.setImage(newUserInfo.getImage());
        return userInfo;
    }
}
