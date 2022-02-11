<%@ page import="edu.epam.util.PropertyReader" %>
<%@ page import="edu.epam.constants.ElibView" %>
<%@ page import="edu.epam.util.ServletUtility" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><%=PropertyReader.getValue("registration")%></title>
    <%--        <meta charset="utf-8" />--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="../assets/css/main.css" />
    <script>
        function validate()
        {
            var name = document.form.name.value;
            var surname = document.form.surname.value;
            var email = document.form.email.value;
            var phone = document.form.phone.value;
            var password = document.form.password.value;
            var pswrepeat = document.form.pswrepeat.value;

            if (name==null || name=="")
            {
                alert("Name can't be blank");
                return false;
            }
            if (surname==null || surname=="")
            {
                alert("Surname can't be blank");
                return false;
            }
            else if (phone==null || phone.length != 10)
            {
                alert("phone should be 10 digit");
                return false;
            }
            else if (email==null || email=="")
            {
                alert("Email can't be blank");
                return false;
            }
            else if(password.match(/^[A-Za-z]\w{7,14}$/))
            {
                alert("Password should contain only characters, numeric digits, underscore and first character must be a letter");
                return false;
            }
            else if (password!=pswrepeat)
            {
                alert("Confirm Password should match with the Password");
                return false;
            }
        }
    </script>
</head>
    <body class="is-preload">
        <div id="wrapper">
            <!-- Main -->
            <div id="main">
                <div class="inner">
                    <jsp:include page = "Header.jsp" />

                    <form action="<%=ElibView.REGISTER%>" method="post" onsubmit="return validate()">
                        <div>
                            <h1>Register</h1>
                            <p><%=ServletUtility.getErrorMessage(request)%></p>
                            <p>Please fill in this form to create an account.</p>
                            <hr>

                            <label for="name"><b>First Name</b></label>
                            <input type="text" placeholder="First Name" name="name" id="name" required>

                            <label for="surname"><b>Last Name</b></label>
                            <input type="text" placeholder="Last Name" name="surname" id="surname" required>

                            <label for="phone"><b>Phone Number</b></label>
                            <input type="text" placeholder="Phone number" name="phone" id="phone" required>

                            <label for="email"><b>Email</b></label>
                            <input type="text" placeholder="Enter Email" name="email" id="email" required>

                            <label for="psw"><b>Password</b></label>
                            <input type="password" placeholder="Enter Password" name="password" id="psw" required>

                            <label for="psw-repeat"><b>Repeat Password</b></label>
                            <input type="password" placeholder="Repeat Password" name="pswrepeat" id="psw-repeat" required>
                            <hr>
                            <button type="submit">Register</button>
                            <%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%>
                        </div>

                        <div class="">
                            <a href="<%=ElibView.LOGIN%>"><%=PropertyReader.getValue("haveAccount?")%></a>
                        </div>
                    </form>
                </div>
            </div>
            <jsp:include page = "Sidebar.jsp" />
        </div>
        <!-- Scripts -->
        <script src="../assets/js/jquery.min.js"></script>
        <script src="../assets/js/browser.min.js"></script>
        <script src="../assets/js/breakpoints.min.js"></script>
        <script src="../assets/js/util.js"></script>
        <script src="../assets/js/main.js"></script>
    </body>
</html>
