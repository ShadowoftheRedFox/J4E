<%@ page import="java.util.List" %>
<%@ page import="fr.cyu.jee.beans.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Employés</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
    <style>
        .inline-form {
            flex-direction: row;
            justify-content: space-evenly;
        }

        td form {
            display: block;

            input {
                display: none;
            }
        }
    </style>
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
            <button type="submit">Ajouter</button>
            <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
                pageContext.request.getAttribute("error")}</label>
        </form>
    </div>
    <div>
        <h1>Employés</h1>
        <table>
            <caption>
                Liste des employés
            </caption>
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Prénom</th>
                <th scope="col">Nom</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <% List<User> users = (List<User>) request.getAttribute("users");
                if (users == null) {
                    return;
                }
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
                    <form method="post" action="${pageContext.request.contextPath}/employee/delete">
                        <input type="number" name="id" value="<%= u.getId()%>">
                        <button type="submit">Effacer</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/employee/edit/<%= u.getId()%>">
                        <button>Modifier</button>
                    </a>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/components/footer.jsp"/>
</body>

</html>