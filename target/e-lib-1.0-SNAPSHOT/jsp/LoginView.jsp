<%@ page import="edu.epam.util.PropertyReader" %>
<%@ page import="edu.epam.constants.ElibView" %>
<%@ page import="edu.epam.util.ServletUtility" %>
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
        <div id="wrapper">
            <!-- Main -->
            <div id="main">
                <div class="inner">
                    <jsp:include page = "Header.jsp" />
                    <p><%=ServletUtility.getSuccessMessage(request)%> </p>
                    <form method="post" action="<%=ElibView.LOGIN%>">
                        <br>${message}
                        <div class="row gtr-uniform">
                            <div class="col-3"></div>
                            <div class="col-6">
                                <h3><%=PropertyReader.getValue("login")%></h3>
                                <div class="col-12-medium">
                                    <label for="email">Email:</label>
                                    <input type="text" name="email" id="email" placeholder="Email" />
                                </div>
                                <div class="col-12-medium">
                                    <label for="password">Password:</label>
                                    <input type="password" name="password" id="password" placeholder="Password" />
                                </div>
                                <input type="submit" value="<%=PropertyReader.getValue("login")%>" class="button primary small " />
                                <a href="<%=ElibView.REGISTER%>"><%=PropertyReader.getValue("noAccount?")%></a>
                            </div>
                            <div class="col-3"></div>
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
