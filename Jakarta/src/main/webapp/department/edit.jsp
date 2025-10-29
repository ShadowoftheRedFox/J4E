<%@ page import="fr.cyu.jee.beans.Department" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Département</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<jsp:include page="/components/header.jsp"/>
<div class="main">
    <%
        Department d = (Department) request.getAttribute("department");
        if (d == null) {
    %>
    <div>
        <p>Département inconnu <%= request.getAttribute("id")%>
        </p>
    </div>
    <%
    } else {
    %>
    <form class="inline-form" method="post" action="${pageContext.request.contextPath}/department/edit/<%= d.getId()%>">
        <label for="name">Nom</label>
        <input type="text" id="name" name="name" required value="<%= d.getName()%>">
        <button type="submit">Ajouter</button>
        <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
            pageContext.request.getAttribute("error")}</label>
    </form>
    <%}%>
</div>
<jsp:include page="/components/footer.jsp"/>
</body>

</html>