<%@ page import="java.util.List" %>
<%@ page import="fr.cyu.jee.beans.User" %>
<%@ page import="fr.cyu.jee.beans.Department" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Employés: Modification</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<jsp:include page="/components/header.jsp"/>
<div class="main">
    <%
        User u = (User) request.getAttribute("user");
        if (u == null) {
    %>
    <div>
        <p>Utilisateur inconnu <%= request.getAttribute("id")%> <%= request.getAttribute("path")%>
        </p>
    </div>
    <%
    } else {
    %>
    <div>
        <form class="inline-form" method="post"
              action="${pageContext.request.contextPath}/employee/edit/<%=u.getId()%>">
            <label for="first_name">Prénom</label>
            <input type="text" id="first_name" name="first_name" required value="<%=u.getFirstName()%>">
            <label for="last_name">Nom</label>
            <input type="text" id="last_name" name="last_name" required value="<%=u.getLastName()%>">
            <label for="password">Mot de passe</label>
            <input type="text" id="password" name="password" required value="<%=u.getPassword()%>">
            <label for="department">Département</label>
            <select id="department" name="department" required>
                <option value="">--Aucun--</option>
                <%
                    List<Department> deps = (List<Department>) request.getAttribute("departments");
                    if (deps != null) {
                        for (Department d : deps) {
                %>
                <option
                    value="<%= d.getId()%>" <%= u.getDepartment() != null && u.getDepartment().getId() == d.getId() ? "selected" : "" %>><%= d.getName()%>
                </option>
                <% }
                } %>
            </select>
            <%-- TODO permissions and ranks --%>
            <button type="submit">Modifier</button>
            <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
                pageContext.request.getAttribute("error")}</label>
        </form>
    </div>
    <% }%>
    <a href="${pageContext.request.contextPath}/employee">Retour au menu</a>
</div>
<jsp:include page="/components/footer.jsp"/>
</body>

</html>