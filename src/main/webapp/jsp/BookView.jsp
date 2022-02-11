<%@ page import="edu.epam.entities.BookOnSite" %>
<%@ page import="edu.epam.constants.ElibView" %>
<%@ page import="edu.epam.entities.Comment" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.epam.entities.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
    <head>
        <title><%=PropertyReader.getValue("org")%></title>
        <%--        <meta charset="utf-8" />--%>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="../assets/css/main.css" />
        <script>
            var request;
            function postComment(){
                var comment=document.commentform.comment.value;
                var userID=document.commentform.userID.value;
                var bookID=document.commentform.bookID.value;
                var isComment = document.commentform.isComment.value;
                var url="http://localhost:8080/e_lib/app/comment?comment="+comment+"&userID="+userID+"&bookID="+bookID+"&isComment="+isComment;
                document.commentform.comment.value = "";
                if(window.XMLHttpRequest){
                    request=new XMLHttpRequest();
                }
                else if(window.ActiveXObject){
                    request=new ActiveXObject("Microsoft.XMLHTTP");
                }
                try{
                    request.onreadystatechange=function(){
                        if(request.readyState==4){
                            var val=request.responseText;
                            document.getElementById('mylocation').innerHTML=val;
                        }
                    }//end of function
                    request.open("GET",url,true);
                    request.send();
                }catch(e){alert("Unable to connect to server");}
            }

            function postSuggestion(){
                var comment=document.suggestionform.suggestion.value;
                var userID=document.suggestionform.userID.value;
                var bookID=document.suggestionform.bookID.value;
                var isComment=document.suggestionform.isComment.value;
                var url="http://localhost:8080/e_lib/app/comment?comment="+comment+"&userID="+userID+"&bookID="+bookID+"&isComment="+isComment;
                document.suggestionform.suggestion.value = "";
                if(window.XMLHttpRequest){
                    request=new XMLHttpRequest();
                }
                else if(window.ActiveXObject){
                    request=new ActiveXObject("Microsoft.XMLHTTP");
                }
                try{
                    request.open("GET",url,true);
                    request.send();
                }catch(e){alert("Unable to connect to server");}
            }
        </script>
    </head>
    <body class="is-preload">
        <%
            User useR = (User) session.getAttribute("user");
            boolean loggedIn = useR != null;
            BookOnSite book = (BookOnSite) request.getAttribute("book");
            List<Comment> comments = (List<Comment>) ((List<?>)request.getAttribute("comments"));
            List<Category> categories = (List<Category>) ((List<?>)request.getAttribute("categories"));
        %>
        <div id="wrapper">
            <!-- Main -->
            <div id="main">
                <div class="inner">
                    <!-- Header -->
                    <jsp:include page = "Header.jsp" />
                    <div class="row gtr-uniform">
                        <div class="col-8">
                            <p><%=PropertyReader.getValue("currentlyReading")%>: <%=book.getCurrentlyReading()%></p>
                            <p><%=PropertyReader.getValue("haveRead")%>: <%=book.getHaveRead()%></p>
                            <p><%=PropertyReader.getValue("wantToRead")%>: <%=book.getWantToRead()%></p>
                            <div class="row gtr-uniform">
                                <div class="col-6">
                                    <div><span class="image fit"><img src="http://127.0.0.1:8080/file:///C:/Users/user/Documents/resources/<%=book.getPictureName()%>" alt="" /></span></div>
                                    <p><strong>Rating: </strong> <% if(book.getVotesNum() == 0) { %>
                                        0 <%  } else { %> <%=book.getVotesSum() / book.getVotesNum()%>
                                        <% } %>
                                    </p>
                                </div>
                                <div class="col-6">
                                    <p><strong><%=book.getAuthor()%></strong></p>
                                    <p><strong><%=book.getName()%></strong></p>
                                    <form action="<%=ElibView.DOWNLOAD%>" method="get">
                                        <input type="hidden" name = "bookID" value = "<%=book.getID()%>">
                                        <input type="hidden" name = "name" value = "<%=book.getName()%>">
                                        <input type="hidden" name = "author" value = "<%=book.getAuthor()%>">
                                        <input type="submit" value="Download">
                                    </form>
                                </div>
                            </div>
                            <%--    <a href="<%=ElibView.DOWNLOAD%>?bookID=<%=book.getID()%>&author=<%=book.getAuthor()%>&name=<%=book.getName()%>">Download</a>--%>
                            <form name="commentform">
                                <%=PropertyReader.getValue("comment")%><br/>
                                <textarea name="comment" id="comment" rows="3"></textarea>
                                <% if (loggedIn){ %>
                                    <input type="hidden" name="userID" value="<%=useR.getID()%>"/>
                                <% } %>
                                <input type="hidden" name="bookID" value="<%=book.getID()%>"/>
                                <input type="hidden" name="isComment" value="1"/>
                                <ul class="actions">
                                    <% if (loggedIn){ %>
                                        <li><input type="button" value="<%=PropertyReader.getValue("add")%>" onclick="postComment()" class="primary" /></li>
                                    <% } else{ %>
                                    <form action="<%=ElibView.LOGIN%>" method="get">
                                        <li><input type="submit" value="<%=PropertyReader.getValue("add")%>" class="primary" /></li>
                                    </form>
                                    <% }%>
                                </ul>
                            </form>

                            <h2>Comments</h2>
                            <div id="mylocation">
                                <% for (Comment comment : comments) { %>
                                <p><strong><%=comment.getDate()%> - <%=comment.getUser_email()%></strong>: <%=comment.getContent()%></p>
                                <% } %>
                            </div>
                        </div>
                        <div class ="col-1"></div>
                        <div class="col-3">
                            <% if (useR != null && !useR.isAdmin()) {%>
                                <form name="suggestionform">
                                    <%=PropertyReader.getValue("makeSuggestion")%><br/>
                                    <textarea name="suggestion" id="suggestion" rows="5"></textarea>
                                    <input type="hidden" name="userID" value="<%=useR.getID()%>"/>
                                    <input type="hidden" name="bookID" value="<%=book.getID()%>"/>
                                    <input type="hidden" name="isComment" value="0"/>
                                    <ul class="actions">
                                        <li><input type="button" value="<%=PropertyReader.getValue("add")%>" onclick="postSuggestion()" class="primary" /></li>
                                    </ul>
                                </form>
                            <% } %>
                        </br>
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
