<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.siit.bookReviewer.repository.UserRepository" %>
<%@ page import="com.siit.bookReviewer.model.User" %>
<%@ page import="com.siit.bookReviewer.service.UserService" %>
<%@ page import="com.siit.bookReviewer.service.BookReviewService" %>


<html>
 <head>
    <!-- This will make the table look nicer -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Very nice user list</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.21.4/dist/bootstrap-table.min.css">
  </head>
<body>
<h2>Registration</h2>

    <form method="post" action="register">
        <div class="form-outline mb-4">
            <input type="text" name="firstName" value="First Name..." onclick="this.value=''"/><br/>
        </div>
        <div class="form-outline mb-4">
                    <input type="text" name="lastName" value="Last Name..." onclick="this.value=''"/><br/>
                </div>
        <div class="form-outline mb-4">
            <input type="text" name="email"  value="Email..." onclick="this.value=''"/><br/>
        </div>
        <div class="form-outline mb-4">
                    <input type="text" name="password" value="Password..." onclick="this.value=''"/><br/>
                </div>

    <br/>
    <input type="submit" value="Register" class="btn btn-primary btn-block"/>
    </form>

    <h2> Already have an account? </h2>
    <button id="myButton" class="float-left submit-button" >Log in</button>

    <script type="text/javascript">
        document.getElementById("myButton").onclick = function () {
            location.href = "login";
        };
    </script>

</body>
</html>
