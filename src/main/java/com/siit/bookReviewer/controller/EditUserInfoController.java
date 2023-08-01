package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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

@WebServlet(name = "EditUserInfoApi", urlPatterns = "/editUser")
public class EditUserInfoController extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(UserController.class);
    ObjectMapper objectMapper = new ObjectMapper();
    private final BookReviewService bookReviewService;
    private final UserService userService;
    private final Gson gson = new Gson();


    public EditUserInfoController() {
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
        log.info("let's edit user...");
        String email = request.getSession().getAttribute("user").toString();
        User loggedInUser = userService.getUserByEmail(email);
        Integer userId = loggedInUser.getId();
        request.getSession().setAttribute("userId", userId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/editUser.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String email = request.getSession().getAttribute("user").toString();
            User loggedInUser = userService.getUserByEmail(email);
            Integer userId = loggedInUser.getId();
            String newFirstName = request.getParameter("newFirstName");
            String newLastName = request.getParameter("newLastName");
            request.getSession().setAttribute("firstName", newFirstName);
            request.getSession().setAttribute("lastName", newLastName);
            request.getSession().setAttribute("userId", userId);
            userService.updateUserInfo(newFirstName, newLastName, email);
            response.sendRedirect("mainPage");
        } catch (EntityNotFoundException | IOException e) {
            log.info(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

}
