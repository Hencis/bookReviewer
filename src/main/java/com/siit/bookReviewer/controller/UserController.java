package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siit.bookReviewer.repository.UserRepository;
import com.siit.bookReviewer.service.UserService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UsersApi", urlPatterns = "/users")
public class UserController extends HttpServlet {

    private Logger log = LoggerFactory.getLogger(UserController.class);
    ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;

    public UserController() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("BookReviewer");
        this.userService = new UserService(
                new UserRepository(entityManagerFactory.createEntityManager())
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("GET all Users...");
        StringBuffer output = new StringBuffer();
        output.append(objectMapper.writeValueAsString(userService.findAll()));
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(output);
    }
}