package com.example.demo5bot.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "vocabulary", schema = "test")
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "integer")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "integer")
    private User user;
    @Column(name = "english_word", nullable = false, columnDefinition = "varchar")
    private String englishWord;
    @Column(name = "translation", nullable = false, columnDefinition = "varchar")
    private String translation;

}