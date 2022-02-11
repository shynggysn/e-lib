<%@ page import="edu.epam.entities.BookOnSite" %>
<%@ page import="java.util.List" %>
<html>
    <head>
        <title><%=PropertyReader.getValue("whatIsElib")%></title>
<%--        <meta charset="utf-8" />--%>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="../assets/css/main.css" />
</head>
<body class="is-preload">
<%
    List<BookOnSite> books;
    books = (List<BookOnSite>) request.getAttribute("books");
%>

<!-- Wrapper -->
<div id="wrapper">

    <!-- Main -->
    <div id="main">
        <div class="inner">

            <!-- Header -->
        <jsp:include page = "Header.jsp" />

        <h1>All books for query"<%=request.getParameter("query")%>"</h1>
        <% if (books != null) { %>
            <h2><%=books.size()%> books</h2>
            <% for (BookOnSite book: books) {%>
            <a href = "<%=ElibView.BOOK%>?bookID=<%=book.getID()%>"><%=book.getAuthor()%> <%=book.getName()%></a>
            </br>
            <% } } else { %>
            <p>nothing found</p>
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
