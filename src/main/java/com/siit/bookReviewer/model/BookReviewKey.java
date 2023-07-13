package com.siit.bookReviewer.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class BookReviewKey implements Serializable {

    private Integer userId;
    private Integer bookId;

}