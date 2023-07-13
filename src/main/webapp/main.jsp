<%@ page import="com.siit.bookReviewer.service.BookService" %>
<%@ page import="com.siit.bookReviewer.repository.BookRepository" %>
<%@ page import="com.siit.bookReviewer.controller.MainPageController" %>
<%@ page import="com.siit.bookReviewer.model.Book" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>


<html>
    <head>
        <meta charset="UTF-8">
        <title>Main page displaying the books and the reviews</title>
    </head>
    <body>
        <h2>Main page</h2>
        <a href="logout.jsp">LogOut</a>
        <h3>Logged in user: <%=request.getSession().getAttribute("user")%></h3>
        <body>
            <h2>list of books</h2>
            <table border="1">
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Genre</th>
                        <th>Description</th>
                        <th>Reviews<th>
                    </tr>
                    <%
                           List<Book> books = (List<Book>) request.getAttribute("bookList");
                           if (books != null) {
                                       for (Book book : books) {
                                   %>
                                   <tr>
                                       <td><%= book.getTitle() %></td>
                                       <td><%= book.getAuthor() %></td>
                                       <td><%= book.getGenre() %></td>
                                       <td><%= book.getDescription() %></td>
                                       <td> <button id=<%=book.getId()%> class="float-left submit-button" value=<%=book.getId()%>>View Reviews</button>

                                                  <script type="text/javascript">
                                                      document.getElementById(<%=book.getId()%>).onclick = function () {
                                                          location.href = "reviews?id=" + <%=book.getId()%>;
                                                      };
                                                  </script> </td>
                                   </tr>
                                   <% } } else { %>
                                   <tr>
                                       <td colspan="4">No books found</td>
                                   </tr>
                                   <% } %>
                               </table>


        </body>

    </body>
</html>