package com.example.be.service;

import com.example.be.model.User;

public interface IUserService {
    User findUserByEmail(String email);

    boolean checkOldPassword(String oldPassword, String password);

    User findById(Integer userId);
}
