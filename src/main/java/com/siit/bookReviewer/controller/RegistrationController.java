package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siit.bookReviewer.model.User;
import com.siit.bookReviewer.repository.UserRepository;
import com.siit.bookReviewer.service.UserService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationController extends HttpServlet {
    private Logger log = LoggerFactory.getLogger(RegistrationController.class);

    ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;

    public RegistrationController() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("BookReviewer");
        this.userService = new UserService(
                new UserRepository(entityManagerFactory.createEntityManager())
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Registration...");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        User user = new User(firstName, lastName, email, password);
        userService.add(user);
        session.setAttribute("user", email);
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
        response.sendRedirect("mainPage");
    }

}