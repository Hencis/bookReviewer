<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.siit.bookReviewer.repository.BookRepository" %>
<%@ page import="com.siit.bookReviewer.controller.BookReviewController" %>
<%@ page import="com.siit.bookReviewer.controller.EditReviewController" %>
<%@ page import="com.siit.bookReviewer.service.BookReviewService" %>
<%@ page import="com.siit.bookReviewer.model.BookReview" %>
<%@ page import="com.siit.bookReviewer.service.UserService" %>
<%@ page import="com.siit.bookReviewer.model.Book" %>
<%@ page import="com.siit.bookReviewer.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>


<html>
     <head>
  <meta charset="UTF-8">
  <h1> Looks like you have changed your mind</h1>
     </head>
     <body>
         <%
              BookReview bookReview = (BookReview) request.getAttribute("bookReview");
          %>
          <h1> Your review for the <%= bookReview.getBook().getTitle() %> book </h1>
            <td>  <p>Review Message: <%= bookReview.getReviewMessage() %></p> </td>
            <td> <p>Rating: <%= bookReview.getRating() %></p> </td>

          <form method="post" action="edit?reviewId=<%=request.getAttribute("reviewId")%>&bookId=<%=request.getAttribute("bookId")%>&userId=<%=request.getAttribute("userId")%>">
                  <div class="form-outline mb-4">
                              <input type="text" name="reviewMessage"  value="Add New Review" onclick="this.value=''"/><br/>
                  </div>
                   <div class="form-outline mb-4">
                                      <label for="rating">Rating</label>
                                        <select name="rating" id="rating">
                                          <option value="1">1</option>
                                          <option value="2">2</option>
                                          <option value="3">3</option>
                                          <option value="4">4</option>
                                          <option value="5">5</option>
                                        </select>
                          </div>
                  <input type="submit" value="Update Review and Rating!" class="btn btn-primary btn-block"/>
           </form>

     </body>
</html>