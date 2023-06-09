package com.cyber.cyber_capstone.controller;


import com.cyber.cyber_capstone.database.entities.User;
import com.cyber.cyber_capstone.database.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            if (user.getUsername() == null || user.getUsername().equals("") || user.getPassword() == null || user.getPassword().equals("")) {
                return new ResponseEntity<>("Login and password can not be empty", HttpStatus.BAD_REQUEST);
            }
            User userFromDB = usersRepository.findByUsername(user.getUsername());
            if (userFromDB == null) {
                System.out.println("User " + user.getUsername() + " was not found");
                return new ResponseEntity<>("User was not found", HttpStatus.BAD_REQUEST);
            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (AuthenticationException authenticationException) {
            System.out.println("Authentication attempt from non-existent user");
            return new ResponseEntity<>("Incorrect login or password", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User userFromDB = usersRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            System.out.println("User " + user.getUsername() + " is already exists");
            return new ResponseEntity<>("User is already exists" , HttpStatus.BAD_REQUEST);
        }
        if (user.getUsername().trim().equals("") || user.getPassword().trim().equals("")) {
            return new ResponseEntity<>("Login and password can not be empty", HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername().trim());
        newUser.setPassword(passwordEncoder.encode(user.getPassword().trim()));
        usersRepository.save(newUser);
        return new ResponseEntity<>("User created successfully", HttpStatus.OK);
    }

}
