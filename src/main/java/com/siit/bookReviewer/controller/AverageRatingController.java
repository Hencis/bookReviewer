package com.siit.bookReviewer.controller;

import com.siit.bookReviewer.repository.BookRepository;
import com.siit.bookReviewer.repository.BookReviewRepository;
import com.siit.bookReviewer.repository.UserRepository;
import com.siit.bookReviewer.service.BookReviewService;
import com.siit.bookReviewer.service.BookService;
import com.siit.bookReviewer.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(name = "AverageRatingApi", urlPatterns = "/averageRating")
public class AverageRatingController extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(UserController.class);
    private final BookReviewService bookReviewService;
    private final UserService userService;
    private final BookService bookService;

    public AverageRatingController() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("BookReviewer");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepositor = new UserRepository(entityManager);
        BookRepository bookRepository = new BookRepository(entityManager);
        BookReviewRepository bookReviewRepository = new BookReviewRepository(entityManager);
        this.bookReviewService = new BookReviewService(bookReviewRepository, userRepositor, bookRepository);
        this.userService = new UserService(userRepositor);
        this.bookService=new BookService(bookRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("GET the average ratings...");
        Integer id = Integer.parseInt(request.getParameter("id"));
        Double averageRating = bookReviewService.calculateAverageRatingForBook(id);
        String bookTitle = bookService.getBookTitleById(id);
        request.setAttribute("bookTitle", bookTitle);
        request.setAttribute("averageRating", averageRating);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/averageRating.jsp");
        dispatcher.forward(request, response);
    }

}
