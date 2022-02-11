<%@ page import="edu.epam.entities.Book" %>
<%@ page import="edu.epam.entities.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.epam.entities.BookOnSite" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>get from bundle</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<%
    List<BookOnSite> books;
    books = (List<BookOnSite>) request.getAttribute("books");

%>
<h2>allBooks by category <<>></h2>
<% for (Book book: books) {%>
<a href="<%=ElibView.BOOK%>?bookID=<%=book.getID()%>"><%=book.getID()%> - <%=book.getAuthor()%> - <%=book.getName()%></a>
<% } %>
<%@ include file="Footer.jsp" %>
</body>
</html>