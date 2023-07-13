package com.siit.bookReviewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siit.bookReviewer.repository.BookRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "BooksApi", urlPatterns = "/books")
public class BookController extends HttpServlet {

    ObjectMapper objectMapper = new ObjectMapper();
    private final BookRepository bookRepository;

    public BookController() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("BookReviewer");
        this.bookRepository = new BookRepository(
                entityManagerFactory.createEntityManager());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer output = new StringBuffer();
        output.append(objectMapper.writeValueAsString(bookRepository.findAll()));
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(output);
    }
}
