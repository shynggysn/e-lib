<%@ page import="edu.epam.constants.ElibView" %>
<%@ page import="edu.epam.util.PropertyReader" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<header id="header">
    <a href="<%=ElibView.INDEX%>" class="logo"><strong>E-lib</strong> <%=PropertyReader.getValue("onlineLibrary")%></a>
    <a href="<%=ElibView.LANGUAGE%>?locale=russian" ><%=PropertyReader.getValue("russian")%></a>
    <a href="<%=ElibView.LANGUAGE%>?locale=english"><%=PropertyReader.getValue("english")%></a>
    <ul class="icons">
        <li><a href="https://www.youtube.com/channel/UCFXgoSXynlxv0KKH9Vv0vZw" class="icon brands fa-youtube"><span class="label">Youtube</span></a></li>
        <li><a href="https://www.facebook.com/people/Shyngys-Nurzhan/100069626165161/" class="icon brands fa-facebook-f"><span class="label">Facebook</span></a></li>
        <li><a href="https://www.instagram.com/_shyngysnurzhan/" class="icon brands fa-instagram"><span class="label">Instagram</span></a></li>
    </ul>
</header>