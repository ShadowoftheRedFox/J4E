<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.atilla.cytraining.user.User" %>
<%-- <%@ page import="org.atilla.cytraining.exercise" %> --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>CY Training - Approbations des exercices</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>

<body>
    <div class="container">
        <h1>Valider les sujets ici</h1>
        <c:forEach var="exercise" items="${exercises}">
            <c:if test="${!exercise.validated}">
                <div class="exercise-card">
                    <p>Exo: ${exercise.title}</p>
                    <p>Description: ${exercise.description}</p>
                    <p>Autheur: ${exercise.author.username}</p>
                    <form action="<c:url value='/dashboard/approve/validate' />" method="post">
                        <input type="hidden" name="id" value="${exercise.id}" />
                        <button type="submit" class="btn btn-approve">Approuver</button>
                    </form>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <hr />
    <div class="container">
        <h1>Désapprouver les sujets ici</h1>
        <c:forEach var="exercise" items="${exercises}">
            <c:if test="${exercise.validated}">
                <div class="exercise-card">
                    <p>Exo: ${exercise.title}</p>
                    <p>Description: ${exercise.description}</p>
                    <p>Autheur: ${exercise.author.username}</p>
                    <form action="<c:url value='/dashboard/approve/unvalidate' />" method="post">
                        <input type="hidden" name="id" value="${exercise.id}" />
                        <button type="submit" class="btn btn-disapprove">Désapprouver</button>
                    </form>
                </div>
            </c:if>
        </c:forEach>
        <a href="<c:url value='/' />" class="btn btn-navigation">Retour accueil</a>
    </div>
</body>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: auto;
            background: white;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            margin-top: 20px;
            text-align: center;
        }
        h1 {
            color: #004080;
            text-align: center;
        }
        .exercise-card {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 5px;
            background-color: #fafafa;
            text-align: left;
        }
        .exercise-card p {
            margin: 5px 0;
        }
        .btn {
            display: inline-block;
            padding: 8px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }
        .btn-approve {
            background-color: #28a745;
            color: white;
        }
        .btn-disapprove {
            background-color: #dc3545;
            color: white;
        }
        .btn-navigation {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            margin-top: 20px;
            text-decoration: none;
            display: inline-block;
        }
    </style>
</html>