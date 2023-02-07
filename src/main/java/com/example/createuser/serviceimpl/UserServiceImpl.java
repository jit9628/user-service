package com.example.createuser.serviceimpl;

import com.example.createuser.entity.UserLogin;
import com.example.createuser.repostery.UserRepository;
import com.example.createuser.service.UserService;
import com.example.createuser.utility.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int register(UserLogin userLogin) {

        userLogin.setPassword(PasswordEncoder.hashPassword(userLogin.getPassword()));
        return userRepository.register(userLogin);
    }

    @Override
    public UserLogin findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public List<UserLogin> findAll() {

        return userRepository.findAll();
    }


}
