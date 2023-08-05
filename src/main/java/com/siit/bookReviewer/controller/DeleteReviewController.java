package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siit.bookReviewer.model.User;
import com.siit.bookReviewer.repository.BookRepository;
import com.siit.bookReviewer.repository.BookReviewRepository;
import com.siit.bookReviewer.repository.UserRepository;
import com.siit.bookReviewer.service.BookReviewService;
import com.siit.bookReviewer.service.BookService;
import com.siit.bookReviewer.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(name = "DeleteReviewerApi", urlPatterns = {"/deleteReviews"})
public class DeleteReviewController extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(UserController.class);
    ObjectMapper objectMapper = new ObjectMapper();
    private final BookReviewService bookReviewService;
    private final UserService userService;
    private final BookService bookService;

    public DeleteReviewController() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("BookReviewer");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepositor = new UserRepository(entityManager);
        BookRepository bookRepository = new BookRepository(entityManager);
        BookReviewRepository bookReviewRepository = new BookReviewRepository(entityManager);
        this.bookReviewService = new BookReviewService(bookReviewRepository, userRepositor, bookRepository);
        this.userService = new UserService(userRepositor);
        this.bookService = new BookService(bookRepository);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Integer bookId = Integer.parseInt(request.getParameter("id"));
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            String email = request.getSession().getAttribute("user").toString();
            User loggedInUser = userService.getUserByEmail(email);
            if (loggedInUser.getId() == userId) {
                log.info("Succesfully deleted review...");
                bookReviewService.deleteReview(userId, bookId);
                response.sendRedirect("reviews?id=" + bookId);
            } else {
                log.info("Can't delete a review which is not yours...");
                response.sendRedirect("errorDelete");
            }
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            throw new ServletException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
