<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.siit.bookReviewer.repository.BookRepository" %>
<%@ page import="com.siit.bookReviewer.controller.BookReviewController" %>
<%@ page import="com.siit.bookReviewer.service.BookReviewService" %>
<%@ page import="com.siit.bookReviewer.model.BookReview" %>
<%@ page import="com.siit.bookReviewer.service.UserService" %>
<%@ page import="com.siit.bookReviewer.model.Book" %>
<%@ page import="com.siit.bookReviewer.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>

<!DOCTYPE html><html lang='en' class=''>
<head><script src='//production-assets.codepen.io/assets/editor/live/console_runner-079c09a0e3b9ff743e39ee2d5637b9216b3545af0de366d4b9aad9dc87e26bfd.js'></script><script src='//production-assets.codepen.io/assets/editor/live/events_runner-73716630c22bbc8cff4bd0f07b135f00a0bdc5d14629260c3ec49e5606f98fdd.js'></script><script src='//production-assets.codepen.io/assets/editor/live/css_live_reload_init-2c0dc5167d60a5af3ee189d570b1835129687ea2a61bee3513dee3a50c115a77.js'></script><meta charset='UTF-8'><meta name="robots" content="noindex"><link rel="shortcut icon" type="image/x-icon" href="//production-assets.codepen.io/assets/favicon/favicon-8ea04875e70c4b0bb41da869e81236e54394d63638a1ef12fa558a4a835f1164.ico" /><link rel="mask-icon" type="" href="//production-assets.codepen.io/assets/favicon/logo-pin-f2d2b6d2c61838f7e76325261b7195c27224080bc099486ddd6dccb469b8e8e6.svg" color="#111" /><link rel="canonical" href="https://codepen.io/frytyler/pen/EGdtg" />

<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css'><script src='https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js'></script>
<style class="cp-pen-styles">@import url(https://fonts.googleapis.com/css?family=Open+Sans);
.btn { display: inline-block; *display: inline; *zoom: 1; padding: 4px 10px 4px; margin-bottom: 0; font-size: 13px; line-height: 18px; color: #333333; text-align: center;text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75); vertical-align: middle; background-color: #f5f5f5; background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6); background-image: -ms-linear-gradient(top, #ffffff, #e6e6e6); background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6)); background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6); background-image: -o-linear-gradient(top, #ffffff, #e6e6e6); background-image: linear-gradient(top, #ffffff, #e6e6e6); background-repeat: repeat-x; filter: progid:dximagetransform.microsoft.gradient(startColorstr=#ffffff, endColorstr=#e6e6e6, GradientType=0); border-color: #e6e6e6 #e6e6e6 #e6e6e6; border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25); border: 1px solid #e6e6e6; -webkit-border-radius: 4px; -moz-border-radius: 4px; border-radius: 4px; -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05); -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05); box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05); cursor: pointer; *margin-left: .3em; }
.btn:hover, .btn:active, .btn.active, .btn.disabled, .btn[disabled] { background-color: #e6e6e6; }
.btn-large { padding: 9px 14px; font-size: 15px; line-height: normal; -webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; }
.btn:hover { color: #333333; text-decoration: none; background-color: #e6e6e6; background-position: 0 -15px; -webkit-transition: background-position 0.1s linear; -moz-transition: background-position 0.1s linear; -ms-transition: background-position 0.1s linear; -o-transition: background-position 0.1s linear; transition: background-position 0.1s linear; }
.btn-primary, .btn-primary:hover { text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25); color: #ffffff; }
.btn-primary.active { color: rgba(255, 255, 255, 0.75); }
.btn-primary { background-color: #4a77d4; background-image: -moz-linear-gradient(top, #6eb6de, #4a77d4); background-image: -ms-linear-gradient(top, #6eb6de, #4a77d4); background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6eb6de), to(#4a77d4)); background-image: -webkit-linear-gradient(top, #6eb6de, #4a77d4); background-image: -o-linear-gradient(top, #6eb6de, #4a77d4); background-image: linear-gradient(top, #6eb6de, #4a77d4); background-repeat: repeat-x; filter: progid:dximagetransform.microsoft.gradient(startColorstr=#6eb6de, endColorstr=#4a77d4, GradientType=0);  border: 1px solid #3762bc; text-shadow: 1px 1px 1px rgba(0,0,0,0.4); box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.5); }
.btn-primary:hover, .btn-primary:active, .btn-primary.active, .btn-primary.disabled, .btn-primary[disabled] { filter: none; background-color: #4a77d4; }
.btn-block { width: 100%; display:block; }

* { -webkit-box-sizing:border-box; -moz-box-sizing:border-box; -ms-box-sizing:border-box; -o-box-sizing:border-box; box-sizing:border-box; }

html { width: 100%; height:100%; overflow:hidden; }

body {
	width: 100%;
	height:100%;
	font-family: 'Open Sans', sans-serif;
	background: #092756;
	background: -moz-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%),-moz-linear-gradient(top,  rgba(57,173,219,.25) 0%, rgba(42,60,87,.4) 100%), -moz-linear-gradient(-45deg,  #670d10 0%, #092756 100%);
	background: -webkit-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%), -webkit-linear-gradient(top,  rgba(57,173,219,.25) 0%,rgba(42,60,87,.4) 100%), -webkit-linear-gradient(-45deg,  #670d10 0%,#092756 100%);
	background: -o-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%), -o-linear-gradient(top,  rgba(57,173,219,.25) 0%,rgba(42,60,87,.4) 100%), -o-linear-gradient(-45deg,  #670d10 0%,#092756 100%);
	background: -ms-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%), -ms-linear-gradient(top,  rgba(57,173,219,.25) 0%,rgba(42,60,87,.4) 100%), -ms-linear-gradient(-45deg,  #670d10 0%,#092756 100%);
	background: -webkit-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%), linear-gradient(to bottom,  rgba(57,173,219,.25) 0%,rgba(42,60,87,.4) 100%), linear-gradient(135deg,  #670d10 0%,#092756 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#3E1D6D', endColorstr='#092756',GradientType=1 );
}


.frame {
    display: flex; /* Use flexbox to make sure frame adjusts to content size */
    justify-content: center; /* Center the content horizontally within the frame */
    align-items: center; /* Center the content vertically within the frame */
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%); /* Center the frame with negative margins */
    padding: 300px; /* Increase padding to create a bigger frame */
     background-color: rgba(240, 240, 240, 0.2); /* Transparent white background */
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); /* Optional: Add a box shadow for a 3D effect */
    border: 10px;
}

.reviews {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -150px 0 0 -150px;
	width:300px;
	height:300px;
}

.reviews h1 { color: #fff; text-shadow: 0 0 10px rgba(0,0,0,0.3); letter-spacing:1px; text-align:center; }

.reviews h3 {
  color: white;
  font-size: 13px;
  text-align: center;
  letter-spacing: 1px;
}

.reviews table {
  background-color: white;
}



 .image-container img {
         display: flex; /* Use flexbox to make sure frame adjusts to content size */
             justify-content: center; /* Center the content horizontally within the frame */
             align-items: center; /* Center the content vertically within the frame */
             position: absolute;
             top: 50%;
             left: 50%;
             transform: translate(-50%, -50%); /* Center the frame with negative margins */
             padding: 300px; /* Increase padding to create a bigger frame */
              background-color: rgba(240, 240, 240, 0.2); /* Transparent white background */
             box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); /* Optional: Add a box shadow for a 3D effect */
             border: 10px;
             opacity: 0.5;
        }

input {
	width: 100%;
	margin-bottom: 10px;
	background: rgba(0,0,0,0.3);
	border: none;
	outline: none;
	padding: 10px;
	font-size: 13px;
	color: #fff;
	text-shadow: 1px 1px 1px rgba(0,0,0,0.3);
	border: 1px solid rgba(0,0,0,0.3);
	border-radius: 4px;
	box-shadow: inset 0 -5px 45px rgba(100,100,100,0.2), 0 1px 1px rgba(255,255,255,0.2);
	-webkit-transition: box-shadow .5s ease;
	-moz-transition: box-shadow .5s ease;
	-o-transition: box-shadow .5s ease;
	-ms-transition: box-shadow .5s ease;
	transition: box-shadow .5s ease;
}
input:focus { box-shadow: inset 0 -5px 45px rgba(100,100,100,0.4), 0 1px 1px rgba(255,255,255,0.2); }
</style>

<div class="image-container">
 <img src="images/bookb.jpeg" alt="Your Transparent Image">
<div class="reviews">
<html>
<head>
  <meta charset="UTF-8">
  <button onclick="goToMainPage()">Back to Main Page</button>

      <script>
          function goToMainPage() {
              window.location.href = "mainPage";
          }
      </script>
  <h1> Reviews for the <%=  request.getAttribute("bookTitle") %> book </h1>
</head>
<body>
<table border="1">
        <tr>
            <tr>
                <th>User</th>
                <th>Review</th>
                <th>Rating</th>
                <th>Delete review</th>
                <th>Edit review</th>
            </tr>
        </tr>
        <%
           int counter = 0;
           List<BookReview> bookReviews = (List<BookReview>) request.getAttribute("bookReviews");
           if (bookReviews != null) {
             for (BookReview review : bookReviews) {
             counter += 1; %>
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
                <td> <form action="edit" method="get">
                  <input type="hidden" name="reviewId" value="<%= review.getId() %>">
                  <input type="hidden" name="bookId" value="<%= review.getBook().getId() %>">
                  <input type="hidden" name="userId" value="<%= review.getUser().getId() %>">
                  <input type="submit" value="Edit Review">
                </form> </td>
                <% if (counter == 1) { %>
                 <button id=<%=review.getBook().getId()%> class="float-left submit-button" value=<%=review.getBook().getId()%>>Average rating for this book</button>
                                                 <script type="text/javascript">
                                                    document.getElementById(<%=review.getBook().getId()%>).onclick = function () {
                                                    location.href = "averageRating?id=" + <%=review.getBook().getId()%>;
                                                    };
                                                  </script>
                 <% } %>




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

 <td> <form action="averageRating?id=<%=request.getAttribute("bookId")%>" method="get">
                   <input type="hidden" name="bookId" value="<%= request.getAttribute("bookId") %>">
                   <input type="submit" value="Average Rating">
                 </form> </td>

 </div>
</body>
</html>