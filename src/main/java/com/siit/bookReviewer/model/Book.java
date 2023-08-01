package com.siit.bookReviewer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    private String description;
    private String genre;

    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "book")
    List<BookReview> ratings;

    public Book(Integer id, String title, String author, String description, String genre) {
        this.id=id;
        this.title=title;
        this.author=author;
        this.description=description;
        this.genre=genre;
    }

    public Book(Integer id, String title, String author) {
        this.id=id;
        this.title=title;
        this.author=author;
    }
}
