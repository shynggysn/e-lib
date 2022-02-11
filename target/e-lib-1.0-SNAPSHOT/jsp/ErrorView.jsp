<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.02.2022
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Exception e = (Exception) request.getAttribute("exception");
%>
<%@ include file="Header.jsp" %>
<h1><%=e.getMessage()%></h1>
<h2><%
    e.printStackTrace();
%></h2>

</body>
</html>
