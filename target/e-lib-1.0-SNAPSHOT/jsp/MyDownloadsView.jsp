<%@ page import="edu.epam.entities.BookOnSite" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.epam.util.PropertyReader" %>
<html>
<head>
    <title><%=PropertyReader.getValue("library")%></title>
    <%--        <meta charset="utf-8" />--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="../assets/css/main.css" />
</head>
<body class="is-preload">
<div id="wrapper">
    <!-- Main -->
    <div id="main">
        <div class="inner">
            <!-- Header -->
            <jsp:include page = "Header.jsp" />
            <%
                List<BookOnSite> books = (List<BookOnSite>) ((List<?>)request.getAttribute("books"));
            %>
            <h1><%=PropertyReader.getValue("myDownloads")%></h1>
            <% for (BookOnSite book: books) {%>
            <a href="<%=ElibView.BOOK%>?bookID=<%=book.getID()%>"><%=book.getID()%> - <%=book.getAuthor()%> - <%=book.getName()%></a>
            <% } %>
        </div>
    </div>
    <%@ include file="Sidebar.jsp" %>
</div>
<!-- Scripts -->
<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/js/browser.min.js"></script>
<script src="../assets/js/breakpoints.min.js"></script>
<script src="../assets/js/util.js"></script>
<script src="../assets/js/main.js"></script>
</body>
</html>
