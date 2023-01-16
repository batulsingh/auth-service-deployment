package com.app.svc_impl;

import com.app.repository.UserRepository;
import com.app.model.UserEntity;
import com.app.svc.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public long getUserIdByName(String userName) {
        UserEntity user = userRepository.findByUsername(userName);
        return user.getId();
    }
}
