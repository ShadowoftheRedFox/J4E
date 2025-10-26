<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cyu.jee.beans.Project" %>
<%@ page import="fr.cyu.jee.beans.User" %>
<%@ page import="fr.cyu.jee.beans.enums.Status" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Projets</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<jsp:include page="/components/header.jsp"/>
<div class="main">
    <div>
        <form class="inline-form" method="post" action="${pageContext.request.contextPath}/project">
            <label for="name">Nom du projet</label>
            <input type="text" id="name" name="name" required>
            <label for="status">Statut</label>
            <select id="status" name="status" required>
                <%
                    for (Status s : Status.values()) {
                %>
                <option value="<%= s.name()%>"><%= s.getStatus()%>
                </option>
                <% } %>
            </select>
            <label>Employés</label>
            <div class="scroll-menu">
                <%
                    List<User> users = (List<User>) request.getAttribute("users");
                    if (users != null) {
                        for (User u : users) {
                %>
                <div>
                    <input type="checkbox" id="user_<%= u.getId()%>" name="users" value="<%= u.getId()%>">
                    <label for="user_<%= u.getId()%>"><%= u.getFirstName()%> <%= u.getLastName()%></label>
                </div>
                <% }
                } %>
            </div>
            <button type="submit">Ajouter</button>
            <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
                pageContext.request.getAttribute("error")}</label>
        </form>
    </div>
    <div>
        <h1>Projets</h1>
        <table>
            <caption>
                <%
                    Collection<Project> projects = (Collection<Project>) request.getAttribute("projects");
                    int projectCount = (projects != null) ? projects.size() : 0;
                %>
                Liste des projets (<%= projectCount%>)
            </caption>
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Nom</th>
                <th scope="col">Statut</th>
                <th scope="col">Employés</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (projects != null) {
                    for (Project p : projects) {
            %>
            <tr>
                <th scope="row">
                    <%= p.getId()%>
                </th>
                <td>
                    <%= p.getName()%>
                </td>
                <td>
                    <%= p.getStatus() != null ? p.getStatus().getStatus() : "Non défini" %>
                </td>
                <td>
                    <%
                        if (p.getUsers() != null && !p.getUsers().isEmpty()) {
                            for (User u : p.getUsers()) {
                    %>
                        <%= u.getFirstName()%> <%= u.getLastName()%><br>
                    <%
                            }
                        } else {
                    %>
                        Aucun
                    <% } %>
                </td>
                <td>
                    <div class="action-menu">
                        <form method="post" action="${pageContext.request.contextPath}/project/delete">
                            <input type="number" name="id" value="<%= p.getId()%>">
                            <button type="submit">Effacer</button>
                        </form>
                        <a href="${pageContext.request.contextPath}/project/edit/<%= p.getId()%>">
                            <button>Modifier</button>
                        </a>
                    </div>
                </td>
            </tr>
            <% }
            } %>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/components/footer.jsp"/>
</body>

</html>