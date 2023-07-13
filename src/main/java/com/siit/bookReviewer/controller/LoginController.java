package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siit.bookReviewer.model.dto.UserLoginDTO;
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
import java.security.InvalidParameterException;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;

    public LoginController() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("BookReviewer");
        this.userService = new UserService(
                new UserRepository(entityManagerFactory.createEntityManager())
        );
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Get Request for the Login page...");
        if (request.getSession().getAttribute("user") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("mainPage").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Logging in...");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        try {
            UserLoginDTO user = userService.checkIfExists(email, password);
            session.setAttribute("user", user.getEmail());
            response.sendRedirect("mainPage");
        } catch (InvalidParameterException e) {
            log.info(e.getMessage());
            session.setAttribute("status", "failed");
            request.getRequestDispatcher("login").forward(request, response);
        }
    }
}