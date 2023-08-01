package com.siit.bookReviewer.repository;

import com.siit.bookReviewer.model.Book;
import com.siit.bookReviewer.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    private List<Book> testingBooks;

    @InjectMocks
    private BookService bookService;


    @BeforeEach
    public void setup() {
        testingBooks = new ArrayList<>();
        Book book1 = new Book(1, "Title1", "author1", "desc1", "genre1");
        Book book2 = new Book(2, "Title2", "author2", "desc2", "genre2");
        testingBooks.add(book1);
        testingBooks.add(book2);
    }

    @Test
    public void testFindAllBooks() {
        when(bookRepository.findAll()).thenReturn(testingBooks);

        List<Book> books = new ArrayList<>(bookService.findAll());
        System.out.println(books);

        assertEquals(testingBooks.size(), books.size());
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.getBookById(1)).thenReturn(testingBooks.get(0));

        Book result = bookService.getBookById(1);

        assertNotNull(result);
    }

    @Test
    public void testGetBookTitleByBookId() {
        when(bookRepository.getBookTitleById(1)).thenReturn(testingBooks.get(0).getTitle());

        String result = bookService.getBookTitleById(1);

        assertEquals("Title1", result);
    }
  
}