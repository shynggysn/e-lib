<%@ page import="edu.epam.entities.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.epam.util.PropertyReader" %>
<%@ page import="edu.epam.entities.Request" %>

<html>
<head>
    <title><%=PropertyReader.getValue("whatIsElib")%></title>
    <%--        <meta charset="utf-8" />--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="../assets/css/main.css" />
</head>
<body class="is-preload">
<%
    List<Book> books = (List<Book>) ((List<?>)request.getAttribute("books"));
    List<Request> requests = (List<Request>) ((List<?>)request.getAttribute("requests"));
%>

<!-- Wrapper -->
<div id="wrapper">

    <!-- Main -->
    <div id="main">
        <div class="inner">
            <!-- Header -->
            <jsp:include page = "Header.jsp" />
            <h1><%=PropertyReader.getValue("userSuggestions")%></h1>
            <h2>Books</h2>
            <% if (books != null){ %>
            <% for (Book book: books) {%>
            <p><%=book.getAuthor()%> - <%=book.getName()%> </p>
            <form action="<%=ElibView.DOWNLOAD%>" method="get">
                <input type="hidden" name = "bookID" value = "<%=book.getID()%>">
                <input type="hidden" name = "name" value = "<%=book.getName()%>">
                <input type="hidden" name = "author" value = "<%=book.getAuthor()%>">
                <input type="submit" value="Download">
            </form>
            <form action="<%=ElibView.DECIDE%>" method="post">
                <input type="hidden" name="bookID" value="<%=book.getID()%>">
                <input type="hidden" name="action" value="accept">
                <input type="hidden" name="type" value="upload">
                <input type="hidden" name="userID" value="<%=book.getSuggestedBy()%>">
                <input type="hidden" name="fileName" value="<%=book.getFileName()%>">
                <input type="submit" value = "accept">
            </form>
            <form action="<%=ElibView.DECIDE%>" method="post">
                <input type="hidden" name="bookID" value="<%=book.getID()%>">
                <input type="hidden" name="action" value="decline">
                <input type="hidden" name="type" value="upload">
                <input type="hidden" name="userID" value="<%=book.getSuggestedBy()%>">
                <input type="hidden" name="fileName" value="<%=book.getFileName()%>">
                <input type="submit" value = "decline">
            </form>
            <% } } else { %>
            <p> no book uploads</p>
            <% } %>

            <h2>Suggestions on books</h2>
            <% if (requests != null){ %>
            <% for (Request req: requests) {%>
            <p><%=req.getDate()%> - userID:<%=req.getUserId()%> - bookID:<%=req.getBookId()%>: <%=req.getContent()%></p>
            <form action="<%=ElibView.DECIDE%>" method="post">
                <input type="hidden" name="bookID" value="<%=req.getBookId()%>">
                <input type="hidden" name="userID" value="<%=req.getUserId()%>">
                <input type="hidden" name="content" value="<%=req.getContent()%>">
                <input type="hidden" name="action" value="accept">
                <input type="hidden" name="type" value="suggestion">
                <input type="submit" value = "accept">
            </form>
            <form action="<%=ElibView.DECIDE%>" method="post">
                <input type="hidden" name="bookID" value="<%=req.getBookId()%>">
                <input type="hidden" name="userID" value="<%=req.getUserId()%>">
                <input type="hidden" name="content" value="<%=req.getContent()%>">
                <input type="hidden" name="action" value="decline">
                <input type="hidden" name="type" value="suggestion">
                <input type="submit" value = "decline">
            </form>
            <% } } else { %>
            <p> no book suggestions</p>
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
