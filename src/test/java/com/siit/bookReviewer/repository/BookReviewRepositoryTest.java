package com.siit.bookReviewer.repository;

import com.siit.bookReviewer.model.Book;
import com.siit.bookReviewer.model.BookReview;
import com.siit.bookReviewer.model.User;
import com.siit.bookReviewer.service.BookReviewService;
import com.siit.bookReviewer.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookReviewRepositoryTest {

    @Mock
    private BookReviewRepository bookReviewRepository;

    private List<BookReview> testingBookReviews;

    @InjectMocks
    private BookReviewService bookReviewService;

    @BeforeEach
    public void setup() {
        testingBookReviews = new ArrayList<>();
        User user1 = new User(1, "FirstName1", "LastName1", "fn1@yahoo.com", "pass1");
        User user2 = new User(2, "FirstName2", "LastName2", "fn2@yahoo.com", "pass2");
        Book book1 = new Book(1, "Title1", "author1", "desc1", "genre1");
        Book book2 = new Book(2, "Title2", "author2", "desc2", "genre2");
        BookReview bookReview1 = new BookReview(1, user1, book1, "rm1", 1);
        BookReview bookReview2 = new BookReview(2, user2, book1, "rm2", 1);
        testingBookReviews.add(bookReview1);
        testingBookReviews.add(bookReview2);
    }

    @Test
    public void testFindAllUsers() {
        when(bookReviewRepository.findAll()).thenReturn(testingBookReviews);

        List<BookReview> bookReviews = new ArrayList<>(bookReviewService.findAll());

        assertEquals(testingBookReviews.size(), bookReviews.size());
    }

    @Test
    public void getReviewsByBookId() {
        when(bookReviewRepository.getReviewsByBookId(1)).thenReturn(testingBookReviews);

       List<BookReview> result = bookReviewService.findAllByBookId(1);

        assertEquals(testingBookReviews, result);

    }

    @Test
    public void getReviewByUserAndBookId() {
        when(bookReviewRepository.getReviewByUserAndBookId(1,1)).thenReturn(testingBookReviews.get(0));

        BookReview bookReviewResult = bookReviewService.getReviewByUserAndBookId(1,1);

        assertEquals(testingBookReviews.get(0), bookReviewResult);
    }

}