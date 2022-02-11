<%@ page import="edu.epam.util.PropertyReader" %>
<%@ page import="edu.epam.constants.ElibView" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title><%=PropertyReader.getValue("org")%></title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="assets/css/main.css" />
    </head>
    <body class="is-preload">
        <div id="wrapper">

            <!-- Main -->
            <div id="main">
                <div class="inner">

                    <!-- Header -->
                    <jsp:include page = "jsp/Header.jsp" />

                    <!-- Banner -->
                    <section id="banner">
                        <div class="content">
                            <header>
                                <h1>E-lib</h1>
                                <p><%=PropertyReader.getValue("yourElib")%></p>
                            </header>
                            <p>E-lib — <%=PropertyReader.getValue("description")%></p>
                            <p><%=PropertyReader.getValue("description2")%></p>
                            <p><%=PropertyReader.getValue("description3")%></p>
                            <ul class="actions">
                                <li><a href="<%=ElibView.ABOUT%>" class="button big"><%=PropertyReader.getValue("whatIsElib")%></a></li>
                            </ul>
                        </div>
                        <span class="image"><img src="./images/logo.jpg" alt=""/></span>
                    </section>
                </div>
            </div>
            <jsp:include page="jsp/Sidebar.jsp" />
        </div>

        <!-- Scripts -->
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/browser.min.js"></script>
        <script src="assets/js/breakpoints.min.js"></script>
        <script src="assets/js/util.js"></script>
        <script src="assets/js/main.js"></script>
    </body>
</html>