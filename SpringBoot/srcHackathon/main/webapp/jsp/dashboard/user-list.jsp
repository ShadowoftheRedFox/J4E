<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.atilla.cytraining.user.User" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #004080;
            color: white;
            padding: 15px;
            text-align: center;
        }
        main {
            width: 90%;
            margin: auto;
            background: white;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            margin-top: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #004080;
            color: white;
        }
        .btn {
            display: inline-block;
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            color: white;
        }
        .btn-certify {
            background-color: #28a745;
        }
        .btn-decertify {
            background-color: #dc3545;
        }
        .btn:hover {
            opacity: 0.8;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        .actions {
            margin-bottom: 20px;
        }
        .certification-subjects {
            margin-top: 30px;
            padding: 15px;
            border-top: 2px solid #ddd;
        }
        select {
            width: 100%;
            height: auto;
        }
    </style>
</head>
<body>
    <header>
        <h1>Tableau de bord administrateur</h1>
    </header>

    <main>
        <h2>Liste des utilisateurs</h2>

        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom d'utilisateur</th>
                    <th>Admin</th>
                    <th>Certification Globale</th>
                    <th>Certifications par matière</th>
                    <th>Actions</th>
                    <th>Certification par matière</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td><c:out value="${user.username}" /></td>
                        <td>${user.admin ? "✔" : "✘"}</td>
                        <td>${user.globallyCertified ? "✔" : "✘"}</td>
                        <td>
                            Liste des matiere ou l'user est certifier :<br>
                            <ul>
                            <c:forEach var="subject" items="${user.certifiedSubjects}">
                                <ol>${subject.name}</ol>
                            </c:forEach>
                            </ul>
                        </td>
                        <td>
                            <!-- Actions pour les certifications -->
                            <form action="<c:url value='/dashboard/certify-global' />" method="post">
                                <input type="hidden" name="userId" value="${user.id}" />
                                <button type="submit" class="btn btn-certify">Certifier Globalement</button>
                            </form>
                            <form action="<c:url value='/dashboard/decertify-global' />" method="post">
                                <input type="hidden" name="userId" value="${user.id}" />
                                <button type="submit"class="btn btn-decertify">Retirer Certif. Globale</button>
                            </form>
                        </td>
                    
                        <!-- Sélection des matières à certifier -->
                        <td>
                            <ul>
                            <c:forEach var="subject" items="${subjects}">
                                <ol>
                                    <form action="<c:url value='/dashboard/certify-subject' />" method="post">
                                        <input type="hidden" name="userId" value="${user.id}">
                                        <input type="hidden" name="subjectId" value="${subject.id}">
                                        <input type="checkbox" id="checkbox-${subject.id}" ${user.getCertifiedSubjects().contains(subject) ? 'checked' : ''}> ${subject.name}
                                        <input type="hidden" id="hidden-input-${subject.id}" name="add" value="${user.getCertifiedSubjects().contains(subject)}">
                                        <button type="submit">Apply Change on this subjects</button>
                                    </form>
                                </ol>
                            </c:forEach>
                            </ul>
                            </form>
                        </td>
                    </tr>
                    
                </c:forEach>
            </tbody>
        </table>
    </main>
</body>
<script>
    function updateHiddenInput(subjectId) {
        // Récupérer la checkbox et l'input caché correspondants
        const checkbox = document.getElementById(`checkbox-${subjectId}`);
        const hiddenInput = document.getElementById(`hidden-input-${subjectId}`);

        // Mettre à jour la valeur de l'input caché en fonction de l'état de la checkbox
        hiddenInput.value = checkbox.checked;
    }
</script>
</html>
