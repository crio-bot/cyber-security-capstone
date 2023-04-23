package com.cyber.cyber_capstone.controller;

import com.cyber.cyber_capstone.database.entities.Message;

import com.cyber.cyber_capstone.database.entities.User;
import com.cyber.cyber_capstone.database.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/messages")
public class MessagesController {
    @Autowired
    private final MessagesRepository messagesRepository;

    public MessagesController(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @GetMapping
    public ResponseEntity<List<Message>> points(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(messagesRepository.findByAuthor(user.getUsername()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> checkResult(@AuthenticationPrincipal User user, @RequestBody Message message) {
        messagesRepository.save(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> clear(@AuthenticationPrincipal User user) {
        messagesRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
