<%@ page import="edu.epam.util.PropertyReader" %>
<%@ page import="edu.epam.constants.ElibView" %>
<%@ page import="edu.epam.util.ServletUtility" %>
<%@ page import="edu.epam.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><%=PropertyReader.getValue("login")%></title>
    <%--        <meta charset="utf-8" />--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="../assets/css/main.css" />
</head>
<body class="is-preload">
<%
    User useR = (User) session.getAttribute("user");
    boolean LoggedIn = useR != null;
%>
    <div id="wrapper">
        <!-- Main -->
        <div id="main">
            <div class="inner">
                <jsp:include page = "Header.jsp" />
                    <form method="post" action="<%=ElibView.PROFILE%>">
                        <div class="row gtr-uniform">
                            <div class="col-3 col-12-xsmall">
                                <input type="text" name="name" id="name" value="<%=useR.getName()%>" />
                            </div>
                            </br>
                            <div class="col-3 col-12-xsmall">
                                <input type="text" name="surname" id="surname" value="<%=useR.getSurname()%>" />
                            </div>
                            </br>
                            <div class="col-3 col-12-xsmall">
                                <p>Email: <%=useR.getMail()%></p>
                                <input type="hidden" name="email" id="email" value="<%=useR.getMail()%>" />
                            </div>
                            </br>
                            <div class="col-3 col-12-xsmall">
                                <input type="password" name="password" id="password" value="<%=useR.getPassword()%>" />
                            </div>
                            </br>
                            <div class="col-3 col-12-xsmall">
                                <input type="phone" name="phone" id="phone" value="<%=useR.getPhoneNumber()%>" />
                            </div>
                            </br>
                        </div>
                        <input type="submit" value="Change"/>
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
