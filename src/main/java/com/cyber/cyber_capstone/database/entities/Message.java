package com.cyber.cyber_capstone.database.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@Data
@Entity
@Table(name = "messages_table")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String text;
    private String time = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
            .format(Calendar.getInstance().getTime());
    @OneToOne
    User author;
    @OneToOne
    User receiver;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && text.equals(message.text) && time.equals(message.time) && author.equals(message.author) && receiver.equals(message.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, time, author, receiver);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}