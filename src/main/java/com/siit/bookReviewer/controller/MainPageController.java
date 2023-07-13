package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.siit.bookReviewer.model.Book;
import com.siit.bookReviewer.repository.BookRepository;
import com.siit.bookReviewer.repository.UserRepository;
import com.siit.bookReviewer.service.BookService;
import com.siit.bookReviewer.service.UserService;
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
import java.util.List;

@WebServlet("/mainPage")
public class MainPageController extends HttpServlet {
    private Logger log = LoggerFactory.getLogger(MainPageController.class);

    private final Gson gson = new Gson();
    ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;
    private final BookService bookService;

    public MainPageController() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("BookReviewer");
        this.userService = new UserService(
                new UserRepository(entityManagerFactory.createEntityManager())
        );
        this.bookService = new BookService(
                new BookRepository(entityManagerFactory.createEntityManager()));
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("GET request for all books ...");
        if (request.getSession().getAttribute("user") == null) {
            log.info("The user must be logged in in order to access the main page.");
            throw new ServletException("The user must be logged in in order to access the main page.");
            // This will be replaced with a custom jsp file to redirect to the Login page
        } else {
            StringBuffer output = new StringBuffer();
            List<Book> books = bookService.findAll();
            request.setAttribute("bookList", books);
            for (Book book : books) {
                output.append((objectMapper.writeValueAsString(book)));
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
            dispatcher.forward(request, response);
        }
    }
}
