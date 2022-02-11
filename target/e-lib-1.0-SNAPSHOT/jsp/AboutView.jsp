<%@ page import="edu.epam.util.PropertyReader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title><%=PropertyReader.getValue("whatIsElib")%></title>
<%--        <meta charset="utf-8" />--%>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="../assets/css/main.css" />
    </head>
    <body class="is-preload">

    <!-- Wrapper -->
    <div id="wrapper">

        <!-- Main -->
        <div id="main">
            <div class="inner">

                <!-- Header -->
                <jsp:include page = "Header.jsp" />

                <!-- Content -->
                <section>
                    <header class="main">
                        <h1><%=PropertyReader.getValue("aboutElib")%></h1>
                    </header>

                    <span class="image main"><img src="../images/logo.jpg" alt="" /></span>
                    <h2><%=PropertyReader.getValue("whatIsIt")%></h2>
                    <hr class="major" />

                    <h2><%=PropertyReader.getValue("howSet")%> E-lib</h2>
                    <p><%=PropertyReader.getValue("howSetBody")%></p>
                    <hr class="major" />

                    <h2><%=PropertyReader.getValue("whereFrom")%></h2>
                    <p><%=PropertyReader.getValue("whereFromBody")%></p>
                </section>

            </div>
        </div>
        <jsp:include page="Sidebar.jsp"/>
    </div>

    <!-- Scripts -->
    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/js/browser.min.js"></script>
    <script src="../assets/js/breakpoints.min.js"></script>
    <script src="../assets/js/util.js"></script>
    <script src="../assets/js/main.js"></script>

    </body>
</html>
