package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.siit.bookReviewer.model.BookReview;
import com.siit.bookReviewer.model.User;
import com.siit.bookReviewer.model.Book;
import com.siit.bookReviewer.repository.BookRepository;
import com.siit.bookReviewer.repository.BookReviewRepository;
import com.siit.bookReviewer.repository.UserRepository;
import com.siit.bookReviewer.service.BookReviewService;
import com.siit.bookReviewer.service.BookService;
import com.siit.bookReviewer.service.UserService;
import jakarta.persistence.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookReviewApi", urlPatterns = "/reviews")
public class BookReviewController extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(UserController.class);
    ObjectMapper objectMapper = new ObjectMapper();
    private final BookReviewService bookReviewService;
    private final UserService userService;
    private final BookService bookService;
    private final Gson gson = new Gson();

    public BookReviewController() {
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
        log.info("GET all Book Reviews...");
        List<BookReview> bookReviews = bookReviewService.findAllByBookId(Integer.parseInt(request.getParameter("id")));
        String bookTitle=bookService.getBookTitleById(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("bookTitle", bookTitle);
        request.setAttribute("bookId", Integer.parseInt(request.getParameter("id")));
        request.setAttribute("bookReviews", bookReviews);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/reviews.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Integer bookId = Integer.parseInt(request.getParameter("id"));
            String email = request.getParameter("email");
            String reviewMessage = request.getParameter("reviewMessage");
            Integer rating = Integer.parseInt(request.getParameter("rating"));
            request.setAttribute("reviewMessage", reviewMessage);
            request.setAttribute("rating", rating);
            bookReviewService.add(email, bookId, reviewMessage, rating);
            response.sendRedirect("reviews?id=" + bookId);
        } catch (ConstraintViolationException e) {
            log.info("Keypair already exists");
            throw new KeyAlreadyExistsException("Review already exists for this book");
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Integer bookId = Integer.parseInt(request.getParameter("id"));
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            String email = request.getSession().getAttribute("user").toString();
            User loggedInUser = userService.getUserByEmail(email);
            if (loggedInUser.getId() == userId) {
                bookReviewService.deleteReview(userId, bookId);
                response.sendRedirect("reviews?id=" + bookId);
            } else {
                throw new ServletException("Can't delete a review which is not yours.");
            }
        } catch (EntityNotFoundException | IOException e) {
            log.info(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

}