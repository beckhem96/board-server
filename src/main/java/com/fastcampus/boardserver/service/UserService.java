package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.UserDTO;

public interface UserService {

    void register(UserDTO userProfile);

    UserDTO login(String id, String password);

    boolean isDuplicateId(String id);

    UserDTO getUserInfo(String userId);

    void updatePassword(String id, String beforePassword, String newPassword);

    void deleteUser(String id, String password);
}
