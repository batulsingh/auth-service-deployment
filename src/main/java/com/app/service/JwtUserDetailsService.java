package com.app.service;

import com.app.Exceptions.UserAlreadyExistsException;
import com.app.dao.UserDao;
import com.app.model.DAOUser;
import com.app.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Date;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    //@Autowired
   // private KafkaTemplate<String, UserDTO> kafkaTemplate;

    //private static final String TOPIC = "Kafka_Example_json12";

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    // function to save user
    public DAOUser save(UserDTO user) throws Exception{

        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setStatus("Blocked");
        DAOUser ExistingUser = userDao.findByUsername(user.getUsername());
        try {
            if (ExistingUser != null) {
                throw new Exception("User ALREADY EXISTS ");
            }
        }
        catch (UserAlreadyExistsException e){
            System.out.println(e);
        }
       // kafkaTemplate.send(TOPIC, new UserDTO(newUser.getUsername(), "", ""));
        return userDao.save(newUser);
    }

    // function to change userstatus
    public DAOUser updateStatus(String username)  {
        DAOUser user2 = userDao.findByUsername(username);
        user2.setStatus("Unblocked");
        return   userDao.save(user2);
//        return user2;
    }
}
