<%@ page import="java.util.List" %>
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
    <div>
        <form class="inline-form" method="post" action="${pageContext.request.contextPath}/department">
            <label for="name">Nom</label>
            <input type="text" id="name" name="name" required>
            <button type="submit">Ajouter</button>
            <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
                pageContext.request.getAttribute("error")}</label>
        </form>
    </div>
    <div>
        <h1>Départements</h1>
        <table>
            <caption>
                Liste des départements
            </caption>
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Nom</th>
            </tr>
            </thead>
            <tbody>
            <% List<Department> deps = (List<Department>) request.getAttribute("departments");
                if (deps != null) {
                    for (Department d : deps) {
            %>
            <tr>
                <th scope="row">
                    <%= d.getId()%>
                </th>
                <td>
                    <%= d.getName()%>
                </td>
                <td>
                    <div class="action-menu">
                        <form method="post" action="${pageContext.request.contextPath}/department/delete">
                            <input type="number" name="id" value="<%= d.getId()%>">
                            <button type="submit">Effacer</button>
                        </form>
                        <a href="${pageContext.request.contextPath}/department/edit/<%= d.getId()%>">
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