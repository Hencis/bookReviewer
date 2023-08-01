package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.siit.bookReviewer.model.BookReview;
import com.siit.bookReviewer.model.User;
import com.siit.bookReviewer.repository.BookRepository;
import com.siit.bookReviewer.repository.BookReviewRepository;
import com.siit.bookReviewer.repository.UserRepository;
import com.siit.bookReviewer.service.BookReviewService;
import com.siit.bookReviewer.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.List;

@WebServlet(name = "EditBookReviewApi", urlPatterns = "/edit")
public class EditReviewController extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(UserController.class);
    ObjectMapper objectMapper = new ObjectMapper();
    private final BookReviewService bookReviewService;
    private final UserService userService;
    private final Gson gson = new Gson();


    public EditReviewController() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookReviewer");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepositor = new UserRepository(entityManager);
        BookRepository bookRepository = new BookRepository(entityManager);
        BookReviewRepository bookReviewRepository = new BookReviewRepository(entityManager);
        this.bookReviewService = new BookReviewService(bookReviewRepository, userRepositor, bookRepository);
        this.userService = new UserService(userRepositor);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("let's edit...");
        StringBuffer output = new StringBuffer();
        Integer bookId = Integer.parseInt(request.getParameter("bookId"));
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        String email = request.getSession().getAttribute("user").toString();
        User loggedInUser = userService.getUserByEmail(email);
        if (loggedInUser.getId() == userId) {
            BookReview bookReview = bookReviewService.getReviewByUserAndBookId(userId, bookId);
            output.append((objectMapper.writeValueAsString(bookReview)));
            request.setAttribute("bookId", bookId);
            request.setAttribute("userId", userId);
            request.setAttribute("reviewId", bookReview.getId());
            request.setAttribute("bookReview", bookReview);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/edit.jsp");
            dispatcher.forward(request, response);
        }
        else {
            throw new ServletException("Can't edit a review which is not yours.");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Integer bookId = Integer.parseInt(request.getParameter("bookId"));
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            String email = request.getSession().getAttribute("user").toString();
            String reviewMessage = request.getParameter("reviewMessage");
            Integer rating = Integer.parseInt(request.getParameter("rating"));
            User loggedInUser = userService.getUserByEmail(email);
            if (loggedInUser.getId() == userId) {
                bookReviewService.updateReview(userId, bookId, reviewMessage, rating);
                BookReview bookReview = bookReviewService.getReviewByUserAndBookId(userId, bookId);
                request.setAttribute("bookReview", bookReview);
                request.setAttribute("reviewMessage", reviewMessage);
                request.setAttribute("rating", rating);
                response.sendRedirect("reviews?id=" + bookId);
            } else {
                throw new ServletException("Can't edit a review which is not yours.");
            }
        } catch (EntityNotFoundException | IOException e) {
            log.info(e.getMessage());
            throw new ServletException(e.getMessage());
        }

    }
}
