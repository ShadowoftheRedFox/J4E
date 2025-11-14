<%@ page import="fr.cyu.jee.beans.Project" %>
<%@ page import="fr.cyu.jee.beans.User" %>
<%@ page import="fr.cyu.jee.beans.enums.Status" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Projets: Modification</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<jsp:include page="/components/header.jsp"/>
<div class="main">
    <%
        Project p = (Project) request.getAttribute("project");
        if (p == null) {
    %>
    <div>
        <p>Projet inconnu <%= request.getAttribute("id")%>
        </p>
    </div>
    <%
    } else {
    %>
    <div>
        <form class="inline-form" method="post"
              action="${pageContext.request.contextPath}/project/edit/<%=p.getId()%>">
            <label for="name">Nom du projet</label>
            <input type="text" id="name" name="name" required value="<%=p.getName()%>">
            <label for="status">Statut</label>
            <select id="status" name="status" required>
                <%
                    for (Status s : Status.values()) {
                %>
                <option
                    value="<%= s.name()%>" <%= p.getStatus() != null && p.getStatus() == s ? "selected" : "" %>><%= s.getStatus()%>
                </option>
                <% } %>
            </select>
            <label>Employés</label>
            <div class="scroll-menu">
                <%
                    List<User> users = (List<User>) request.getAttribute("users");
                    if (users != null) {
                        for (User u : users) {
                            boolean isSelected = p.getUsers() != null && p.getUsers().contains(u);
                %>
                <div>
                    <input type="checkbox" id="user_<%= u.getId()%>" name="users" value="<%= u.getId()%>" <%= isSelected ? "checked" : "" %>>
                    <label for="user_<%= u.getId()%>"><%= u.getFirstName()%> <%= u.getLastName()%></label>
                </div>
                <% }
                } %>
            </div>
            <button type="submit">Modifier</button>
            <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
                pageContext.request.getAttribute("error")}</label>
        </form>
    </div>
    <% }%>
    <a href="${pageContext.request.contextPath}/project">Retour au menu</a>
</div>
<jsp:include page="/components/footer.jsp"/>
</body>

</html>
