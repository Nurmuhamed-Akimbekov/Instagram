package peaksoft.service;

import peaksoft.entity.UserInfo;
import peaksoft.exception.NotFoundexception;

public interface UserInfoService {
    UserInfo findUserInfoByUserId(Long userId) throws NotFoundexception;
    UserInfo updateUserInfo(Long userinfoId,UserInfo newUserInfo);


}
