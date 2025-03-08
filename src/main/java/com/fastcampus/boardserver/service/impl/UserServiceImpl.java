package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void register(UserDTO userProfile) {

    }

    @Override
    public UserDTO login(String id, String password) {
        return null;
    }

    @Override
    public boolean isDuplicateId(String id) {
        return false;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return null;
    }

    @Override
    public void updatePassword(String id, String beforePassword, String newPassword) {

    }

    @Override
    public void deleteUser(String id, String password) {

    }
}
