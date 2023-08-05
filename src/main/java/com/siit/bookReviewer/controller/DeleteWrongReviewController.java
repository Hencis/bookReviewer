package com.siit.bookReviewer.controller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteWrongReviewControllerApi", urlPatterns = {"/errorDelete"})
public class DeleteWrongReviewController extends HttpServlet {
    public DeleteWrongReviewController() {

    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/errorDelete.jsp");
        dispatcher.forward(request, response);
    }
}
