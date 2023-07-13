<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.siit.bookReviewer.repository.BookRepository" %>
<%@ page import="com.siit.bookReviewer.controller.BookReviewController" %>
<%@ page import="com.siit.bookReviewer.service.BookReviewService" %>
<%@ page import="com.siit.bookReviewer.model.BookReview" %>
<%@ page import="com.siit.bookReviewer.model.Book" %>
<%@ page import="com.siit.bookReviewer.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>


<html>
<head>
  <meta charset="UTF-8">
  <h1> Welcome to reviews</h1>
</head>
<body>
<h2>The reviews of the book</h2>
<table border="1">
        <tr>
            <tr>
                <th>User</th>
                <th>Review</th>
                <th>Rating</th>
                <th>Delete review</th>
            </tr>
        </tr>
        <%
          List<BookReview> bookReviews = (List<BookReview>) request.getAttribute("bookReviews");
           if (bookReviews != null) {
             for (BookReview review : bookReviews) { %>
            <tr>
                <td><%= review.getUser().getFirstName() %></td>
                <td><%= review.getReviewMessage() %></td>
                <td><%= review.getRating() %></td>
                <td> <button id=<%=review.getUser().getId()%> class="float-left submit-button" value=<%=review.getUser().getId()%>>Delete Review</button>

                                                                  <script type="text/javascript">
                                                                      document.getElementById(<%=review.getUser().getId()%>).onclick = function () {
                                                                        fetch("reviews?id=" + <%=review.getBook().getId()%> + "&userId=" + <%=review.getUser().getId()%>, {
                                                                          method: 'DELETE'
                                                                        })
                                                                      };
                                                                  </script> </td>

            </tr>
            <% } }
            else { %>
                         <tr>
                          <td colspan="4">No bookReviews found</td>
                         </tr>
              <% } %>
        </table>


</br>
</br>
<form method="post" action="reviews?id=<%=request.getAttribute("bookId")%>&email=<%=request.getSession().getAttribute("user")%>">
        <div class="form-outline mb-4">
                    <input type="text" name="reviewMessage"  value="Add Review" onclick="this.value=''"/><br/>
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
        <input type="submit" value="Add Review and Rating!" class="btn btn-primary btn-block"/>
 </form>
</body>
</html>