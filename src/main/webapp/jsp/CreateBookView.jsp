<%@ page import="edu.epam.constants.ElibView" %>
<%@ page import="edu.epam.entities.User" %>
<%@ page import="edu.epam.util.PropertyReader" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
  <head>
    <title><%=PropertyReader.getValue("org")%></title>
    <%--        <meta charset="utf-8" />--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="../assets/css/main.css" />
  </head>
  <body class="is-preload">
  <%
    User useR = (User) session.getAttribute("user");
    boolean LoggedIn = useR != null;
  %>
    <div id="wrapper">

      <!-- Main -->
      <div id="main">
        <div class="inner">
          <!-- Header -->
          <jsp:include page = "Header.jsp" />

          <form action="<%=ElibView.UPLOAD_BOOK%>" method="post" enctype="multipart/form-data">
            <table>
              <tr>
                <% if(useR.isAdmin()) {%>
                <td colspan="2"><%=PropertyReader.getValue("uploadBook")%></td>
                <% } else { %>
                <td colspan="2"><%=PropertyReader.getValue("suggestBook")%></td>
                <% }%>
              </tr>
              <tr>
                <td>Book Name</td>
                <td>
                  <input type="text" required="" name="name">
                </td>
              </tr>
              <tr>
                <td>Author</td>
                <td>
                  <input type="text" required="" name="author">
                </td>
              </tr>
              <tr>
                <td>Select File</td>
                <td>
                  <input type="file" required="" name="file">
                </td>
              </tr>
              <tr>
                <td>Select picture</td>
                <td>
                  <input type="file" required="" name="picture">
                </td>
              </tr>
              <%
                if (LoggedIn && !useR.isAdmin()){
              %>
              <tr>
                <td>
                  <input type="hidden" name="user_id" value="<%=useR.getID()%>">
                </td>
              </tr>
              <% } %>
              <tr>
                <td></td>
                <td><input type="submit" value="Submit"></td>
              </tr>
            </table>
          </form>
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