package com.example.demo5bot.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user", schema = "test")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "integer")
    private int id;
    @Column(name = "user_telegram_id", nullable = false, columnDefinition = "bigint")
    private long userId;
    @Column(name = "name", nullable = false, columnDefinition = "varchar")
    private String name;

}