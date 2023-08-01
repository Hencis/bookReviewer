package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siit.bookReviewer.model.User;
import com.siit.bookReviewer.repository.UserRepository;
import com.siit.bookReviewer.service.UserService;
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

@WebServlet(name = "DeleteUsersApi", urlPatterns = "/deleteUser")
public class DeleteUserController extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public DeleteUserController() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("BookReviewer");
        this.userService = new UserService(
                new UserRepository(entityManagerFactory.createEntityManager())
        );
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String email = request.getSession().getAttribute("user").toString();
            User loggedInUser = userService.getUserByEmail(email);
            Integer userId = loggedInUser.getId();
            request.getSession().setAttribute("userId", userId);
            if (loggedInUser.getId() == userId) {
                userService.deleteAccount(email);
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                throw new ServletException("Can't delete account which is not yours.");
            }
        } catch (EntityNotFoundException | IOException e) {
            log.info(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

}
