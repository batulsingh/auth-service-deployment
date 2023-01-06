package com.app.controller;


import com.app.model.JwtRequest;
import com.app.model.JwtResponse;
import com.app.config.JwtTokenUtil;
import com.app.model.UserDTO;
import com.app.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import org.springframework.kafka.core.KafkaTemplate;
/*Spring RestController annotation is used to create RESTful web services using Spring MVC.
Spring RestController takes care of mapping request data to the defined request handler method.
Once response body is generated from the handler method, it converts it to JSON or XML response.*/

/* @CrossOrigin is used to allow cross-origin requests (to BACKEND in this case),
 which are disabled by default by spring-security*/

@RestController
@CrossOrigin("*")
public class JwtAuthenticationController {


   @Autowired
    private KafkaTemplate<String, UserDTO> kafkaTemplate;

    private static final String TOPIC ="Kafka_Example_json12";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private ResponseEntity responseEntity;
    // "/authenticate" endpoint is exposed here
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/authenticateAdmin", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationTokenAdmin(@RequestBody JwtRequest authenticationRequest) throws Exception{
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateTokenAdmin(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    // "/register" endpoint is exposed here
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {


        return ResponseEntity.ok(userDetailsService.save(user));
    }

    @RequestMapping(value = "/activate", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStatus(@RequestParam String numberAsString){
        return new ResponseEntity<>(userDetailsService.updateStatus(numberAsString), HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
         kafkaTemplate.send(TOPIC, new UserDTO(username,"",""));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
