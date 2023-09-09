package com.example.demo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "v")
public class Song {
    @Id // inicializado para tener id continuos
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String artist;

    private String album;

    private Date releaseDate;

    private String genre;

    private Integer duration;

    private String coverImage;

    private String spotifyUrl;

    // TODO: Añadir constructores, getters y setters
}