package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.DuplicateIdException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.fastcampus.boardserver.utils.SHA256Util.encryptSHA256;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private UserProfileMapper userProfileMapper;
    public UserServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }
    @Override
    public void register(UserDTO userProfile) {
        boolean dupleIdResult = isDuplicateId(userProfile.getUserId());
        if (dupleIdResult) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }
        userProfile.setCreateTime(new Date());
        userProfile.setPassword(encryptSHA256(userProfile.getPassword()));
        int insertCount = userProfileMapper.register(userProfile);

        if (insertCount != 1) {
            log.error("insertMember Error! {}", userProfile);
            throw new RuntimeException(
                    "insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "params : " + userProfile);
        }
    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptPassword = encryptSHA256(password);
        return userProfileMapper.findByUserIdAndPassword(id, cryptPassword);
    }

    @Override
    public boolean isDuplicateId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String newPassword) {
        String cryptPassword = encryptSHA256(beforePassword);
        UserDTO user = userProfileMapper.findByIdAndPassword(id, cryptPassword);

        if (user != null) {
            user.setPassword(encryptSHA256(newPassword));
            int insertCount = userProfileMapper.updatePassword(user);
            if (insertCount != 1) {
                log.error("updatePassword Error! {}", user);
                throw new RuntimeException("비밀번호 변경에 실패했습니다.");
            }
        } else {
            log.error("updatePassword Error! {}", user);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void deleteUser(String id, String password) {
        String cryptPassword = encryptSHA256(password);
        UserDTO user = userProfileMapper.findByUserIdAndPassword(id, cryptPassword);
        System.out.println("lwt check:" + user.getUserId());
        System.out.println("lwt check:" + cryptPassword);
        if (user != null) {
            userProfileMapper.deleteUserProfile(user.getUserId());
        } else {
            log.error("deleteUser Error! {}", user);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

    }
}
