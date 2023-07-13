package com.siit.bookReviewer.service;

import com.siit.bookReviewer.model.Book;
import com.siit.bookReviewer.model.BookReview;
import com.siit.bookReviewer.model.User;
import com.siit.bookReviewer.repository.BookRepository;
import com.siit.bookReviewer.repository.BookReviewRepository;
import com.siit.bookReviewer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
public class BookReviewService {

    private final Logger log = LoggerFactory.getLogger(BookReviewService.class);

    private final BookReviewRepository bookReviewRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    public List<BookReview> findAll() {
        return bookReviewRepository.findAll();
    }

    public void add(String email, int bookId, String reviewMessage, Integer rating) {
        User user = userRepository.getUserByEmail(email);
        Book book = bookRepository.getBookById(bookId);
        bookReviewRepository.add(user, book, reviewMessage, rating);
    }

    public List<BookReview> findAllByBookId(int bookId) {
        return (List<BookReview>) bookReviewRepository.getReviewsByBookId(bookId);
    }

    public int deleteReview(Integer userId, Integer bookId) {
        return bookReviewRepository.deleteReview(userId, bookId);
    }
}
