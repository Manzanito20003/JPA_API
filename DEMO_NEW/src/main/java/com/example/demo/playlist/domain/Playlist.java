package com.example.demo.playlist.domain;

import jakarta.persistence.*;
import java.util.List;

import com.example.demo.song.domain.Song;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "playlist_song", joinColumns = { @JoinColumn(name = "playlist_id") }, inverseJoinColumns = {
            @JoinColumn(name = "song_id") })
    private List<Song> songs;

    private String coverImage;

    public Playlist() {
    }

    public Playlist(Long id, String title, List<Song> songs, String coverImage) {
        this.id = id;
        this.title = title;
        this.songs = songs;
        this.coverImage = coverImage;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter y Setter para 'songs'
    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    // Fin Getter y Setter para 'songs'

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
