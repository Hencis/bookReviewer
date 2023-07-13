package com.siit.bookReviewer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_review")
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "review_message")
    private String reviewMessage;

    private Integer rating;

    public BookReview(User user, Book book, String reviewMessage, Integer rating) {
        this.user = user;
        this.book = book;
        this.reviewMessage = reviewMessage;
        this.rating = rating;
    }
}
