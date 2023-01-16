package com.app.service;

import com.app.Exceptions.UserAlreadyExistsException;
import com.app.repository.UserRepository;
import com.app.model.UserEntity;
import com.app.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    //@Autowired
   // private KafkaTemplate<String, UserDTO> kafkaTemplate;

    //private static final String TOPIC = "Kafka_Example_json12";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    // function to save user
    public UserEntity save(UserDTO user) throws Exception{

        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setStatus("Blocked");
        UserEntity ExistingUser = userRepository.findByUsername(user.getUsername());
        if (ExistingUser != null) {
            throw new UserAlreadyExistsException("Username Unavailable");
        }

       // kafkaTemplate.send(TOPIC, new UserDTO(newUser.getUsername(), "", ""));
        return userRepository.save(newUser);
    }

    // function to change userstatus
    public UserEntity updateStatus(String username)  {
        UserEntity user2 = userRepository.findByUsername(username);
        user2.setStatus("Unblocked");
        return   userRepository.save(user2);
//        return user2;
    }
}
