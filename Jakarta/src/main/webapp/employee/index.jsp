<%@ page import="java.util.List" %>
<%@ page import="fr.cyu.jee.beans.User" %>
<%@ page import="fr.cyu.jee.beans.Department" %>
<%@ page import="fr.cyu.jee.beans.enums.Permission" %>
<%@ page import="fr.cyu.jee.beans.enums.Rank" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Employés</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<jsp:include page="/components/header.jsp"/>
<div class="main">
    <div>
        <form class="inline-form" method="post" action="${pageContext.request.contextPath}/employee">
            <label for="first_name">Prénom</label>
            <input type="text" id="first_name" name="first_name" required>
            <label for="last_name">Nom</label>
            <input type="text" id="last_name" name="last_name" required>
            <label for="password">Mot de passe</label>
            <input type="text" id="password" name="password" required>
            <label for="department">Département</label>
            <select id="department" name="department" required>
                <%
                    List<Department> deps = (List<Department>) request.getAttribute("departments");
                    if (deps != null) {

                        for (Department d : deps) {
                %>
                <option value="<%= d.getId()%>"><%= d.getName()%>
                </option>
                <% }
                } %>
            </select>
            <div class="scroll-menu">
                <%
                    for (Permission p : Permission.values()) {
                %>
                <div>
                    <input type="checkbox" id="<%= p.name()%>" name="<%= p.name()%>"> 
                    <label for="<%= p.name()%>"><%= p.getPermission() %></label>
                </div>
                <%
                    }
                %>
            </div>
            <div class="scroll-menu">
                <%
                for (Rank r : Rank.values()) {
                %>
                <div>
                    <input type="checkbox" id="<%= r.name()%>" name="<%= r.name()%>">
                    <label for="<%= r.name()%>"><%= r.getRank() %></label>
                </div>
                <%
                }
                %>
            </div>
            <button type="submit">Ajouter</button>
            <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
                pageContext.request.getAttribute("error")}</label>
        </form>
    </div>
    <div>
        <h1>Employés</h1>
        <table>
            <caption>
                Liste des employés (<%= ((List<User>) request.getAttribute("users")).size()%>)
            </caption>
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Prénom</th>
                <th scope="col">Nom</th>
                <th scope="col">Département</th>
                <th scope="col">Permission(s)</th>
                <th scope="col">Rôle(s)</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <% List<User> users = (List<User>) request.getAttribute("users");
                if (users != null) {
                    for (User u : users) {
            %>
            <tr>
                <th scope="row">
                    <%= u.getId()%>
                </th>
                <td>
                    <%= u.getFirstName()%>
                </td>
                <td>
                    <%= u.getLastName()%>
                </td>
                <td>
                    <%= u.getDepartment() != null ? u.getDepartment().getName() : "Aucun" %>
                </td>
                <td>
                    <%= u.getPermissions().isEmpty() ? "Aucune" : u.getPermissions().toString() %>
                </td>
                <td>
                    <%= u.getRanks().isEmpty() ? "Aucun" : u.getRanks().toString() %>
                </td>
                <td>
                    <div class="action-menu">
                        <form method="post" action="${pageContext.request.contextPath}/employee/delete">
                            <input type="number" name="id" value="<%= u.getId()%>">
                            <button type="submit">Effacer</button>
                        </form>
                        <a href="${pageContext.request.contextPath}/employee/edit/<%= u.getId()%>">
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