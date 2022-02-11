<%@ page import="edu.epam.entities.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.epam.entities.BookOnSite" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title><%=PropertyReader.getValue("library")%></title>
        <%--        <meta charset="utf-8" />--%>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="../assets/css/main.css" />
    </head>
    <body class="is-preload">
        <%
            List<Category> categories;
            List<BookOnSite> books;
            categories = (List<Category>) ((List<?>)request.getAttribute("categories"));
            books = (List<BookOnSite>) request.getAttribute("books");
        %>
        <div id="wrapper">
            <!-- Main -->
            <div id="main">
                <div class="inner">
                    <!-- Header -->
                    <jsp:include page = "Header.jsp" />
                    <div class="row gtr-uniform">
                        <div class="col-8">
                            <h4><%=PropertyReader.getValue("allBooks")%></h4>
                            <div class="box alt">
                                <div class="row gtr-50 gtr-uniform">
                                    <% for (BookOnSite book: books) {%>
                                    <div class="col-2">
                                        <span class="image fit">
                                            <img src="../images/pic01.jpg" alt="" />
                                            <a href="<%=ElibView.BOOK%>?bookID=<%=book.getID()%>"><%=book.getName()%></a>
                                            <a href="<%=ElibView.SEARCH%>?query=<%=book.getAuthor()%>"><%=book.getAuthor()%></a>
                                        </span>
                                    </div>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                        <div class ="col-2"></div>
                        <div class="col-2">
                            <h4><%=PropertyReader.getValue("categories")%></h4>
                            <h5><%=PropertyReader.getValue("numOfCategories",String.valueOf(categories.size()))%></h5>
                            <ul class="alt">
                                <%
                                    if(PropertyReader.rb.getLocale().equals(PropertyReader.en_US)){
                                        for (Category category: categories)
                                        {%>
                                        <li><a href="<%=ElibView.CATEGORY%>?categoryID=<%=category.getID()%>"><%=category.getCategoryNameEN()%></a></li>
                                    <% }}
                                    else{
                                        for (Category category: categories)
                                        {%>
                                        <li href="<%=ElibView.CATEGORY%>?categoryID=<%=category.getID()%>"><%=category.getCategoryNameRU()%></li>
                                    <% }} %>
                            </ul>
                        </div>
                    </div>
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
