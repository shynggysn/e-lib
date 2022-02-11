<%@ page import="edu.epam.util.PropertyReader" %>
<%@ page import="edu.epam.constants.ElibView" %>
<%@ page import="edu.epam.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!-- Sidebar -->
<%
  User user = (User) session.getAttribute("user");
  boolean userLoggedIn = user != null;
%>
<div id="sidebar">
  <div class="inner">

    <!-- Search -->
    <section id="search" class="alt">
      <form action = "<%=ElibView.SEARCH%>" method = "get">
        <input type="text" name = "query" value="<%=PropertyReader.getValue("bookOrAuthor")%>">
      </form>
    </section>

    <!-- Menu -->
    <nav id="menu">
      <ul>
        <li><a href="<%=ElibView.INDEX%>"><%=PropertyReader.getValue("homepage")%></a></li>
        <li><a href="<%=ElibView.CATALOG%>"><%=PropertyReader.getValue("library")%></a></li>
        <%if (userLoggedIn) {%>
        <%if(user.isAdmin()){%>
        <li><a href="<%=ElibView.USER_SUGGESTIONS%>"><%=PropertyReader.getValue("userSuggestions")%></a></li>
        <li><a href="<%=ElibView.UPLOAD_BOOK%>"><%=PropertyReader.getValue("uploadBook")%></a></li>
        <%} else { %>
        <li>
          <span class="opener"><%=PropertyReader.getValue("myBooks")%></span>
          <ul>
            <li><a href="<%=ElibView.MY_DOWNLOADS%>?userID=<%=user.getID()%>"><%=PropertyReader.getValue("myDownloads")%></a></li>
            <li><a href="<%=ElibView.MY_WISH_LIST%>?userID=<%=user.getID()%>"><%=PropertyReader.getValue("myWishList")%></a></li>
          </ul>
        </li>
        <li><a href="<%=ElibView.UPLOAD_BOOK%>"><%=PropertyReader.getValue("suggestBook")%></a></li>
        <% } %>
        <li><a href="<%=ElibView.PROFILE%>"><%=PropertyReader.getValue("profile")%></a></li>
        <li><a href="<%=ElibView.LOGOUT%>"><%=PropertyReader.getValue("logout")%></a></li>
        <%} else{ %>
        <li><a href="<%=ElibView.LOGIN%>"><%=PropertyReader.getValue("login")%></a></li>
        <%} %>
      </ul>
    </nav>

    <!-- Section -->
    <section>
      <header class="major">
        <h2><%=PropertyReader.getValue("getInTouch")%></h2>
      </header>
      <p><%=PropertyReader.getValue("contactUs")%></p>
      <ul class="contact">
        <li class="icon solid fa-envelope"><p>nurzhanshyngys@gmail.com</p></li>
        <li class="icon solid fa-phone">+7 (747) 304-10-05</li>
        <li class="icon solid fa-home"><%=PropertyReader.getValue("address")%><br />
          <%=PropertyReader.getValue("city")%></li>
      </ul>
    </section>

    <!-- Footer -->
    <footer id="footer">
      <p class="copyright">&copy; <%=PropertyReader.getValue("copyright")%></p>
    </footer>

  </div>
</div>