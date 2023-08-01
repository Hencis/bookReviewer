package com.siit.bookReviewer.service;

import com.siit.bookReviewer.model.Book;
import com.siit.bookReviewer.repository.BookRepository;

import java.util.List;


public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {return bookRepository.findAll();}

    public Book getBookById(Integer bookId) {
        return bookRepository.getBookById(bookId);
    }

    public String getBookTitleById(Integer bookId) {
        return bookRepository.getBookTitleById(bookId);
    }


}