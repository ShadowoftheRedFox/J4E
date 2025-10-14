<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: scp-079
  Date: 10/10/25
  Time: 11:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<ol>
<%
    List<String> list = ((List<String>)request.getAttribute("profiles"));

    for (int i = 0; i<list.size(); i++) {
        %>
    <li>$<%= i%> <%= list.get(i)%></li>
<%
    }
%>
</ol>
</body>
</html>
