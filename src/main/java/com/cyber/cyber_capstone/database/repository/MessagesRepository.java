package com.cyber.cyber_capstone.database.repository;

import com.cyber.cyber_capstone.database.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Message, Long> {
    List<Message> findByAuthor(String username);
    void deleteMessagesByAuthor(String username);
}