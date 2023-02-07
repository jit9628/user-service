package com.example.createuser.service;

import com.example.createuser.entity.UserLogin;

import java.util.List;

public interface UserService {

    public int register(UserLogin userLogin);
    public UserLogin findByUserName(String username);
    public List<UserLogin> findAll();


}
