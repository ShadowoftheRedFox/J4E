<%@ page import="org.atilla.cytraining.user.User" %>
<%@ page import="org.atilla.cytraining.subjects.Subject" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>CY Training - Gestion Des Matieres</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>

<body>
    <h1> Ici vous pouvez gerer les matiere (en creer et en supprimer)</h1>
    <h1>Gestion des Matières</h1>

        Formulaire pour ajouter une matière
        <form action="<c:url value='/dashboard/subjects/addSubject' />" method="post">
            <label for="name">Nom de la matière :</label>
            <input type="text" id="name" name="name"required>
            <button type="submit">Ajouter</button>
        </form>

        <h2>Liste des Matières</h2>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Nom</th>
                    <c:if test="${isAdmin}">
                        <th>Actions</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="subject" items="${subjects}">
                    <tr>
                        <td><c:out value="${subject.name}" /></td>
                        <c:if test="${isAdmin}">
                            <td>
                                <form action="<c:url value='/dashboard/subjects/removeSubject' />" method="post">
                                    <input type="hidden" name="id" value="${subject.id}" />
                                    <button type="submit">Supprimmer la matière <br>(peux ne pas marcher si des questions sont liées a la matière ou si des users sont liés)</button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
</body>
</html>