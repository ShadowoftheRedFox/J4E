<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page d'Administration</title>
    <link rel="stylesheet" href="styles.css">
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
        h2, h3 {
            color: #004080;
        }
        .section {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 5px;
            background-color: #fafafa;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            text-decoration: none;
            color: white;
        }
        .btn-primary {
            background-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <section class="container">
        <c:if test="${isAdmin}">
            <h2>Bienvenue, Administrateur</h2>
            <p>Vous avez accès à toutes les fonctionnalités d'administration.</p>
        </c:if>

        <!-- Section gestion des questions -->
        <c:if test="${isAdmin || isGlobalyCertified}">
            <div id="manage-exercies" class="section">
                <h3>Gestion des exercices</h3>
                <p>Consulter, approuver ou rejeter les exercices soumises par les créateurs.</p>
                <button class="btn btn-primary" onclick="window.location.href='/dashboard/approve'">Approuver des exercices</button>
            </div>
        </c:if>

        <!-- Section gestion des utilisateurs -->
        <c:if test="${isAdmin}">
            <div id="manage-users" class="section">
                <h3>Gestion des Utilisateurs</h3>
                <p>Voir la liste des utilisateurs et leurs historiques de réponses.</p>
                <button class="btn btn-primary" onclick="window.location.href='/dashboard/user-list'">Voir les Utilisateurs</button>
            </div>
        </c:if>

        <!-- Section suppression des questions -->
        <div id="create-exercices" class="section">
            <h3>Créer un exercice</h3>
            <p>Créer un nouveau QCM.</p>
            <button class="btn btn-primary" onclick="window.location.href='/dashboard/questions'">Menu création</button>
        </div>
    
        <c:if test="${isAdmin || isGlobalyCertified}">
            <!-- Section gestion sujets -->
            <div id="manage-subjects" class="section">
                <h3>Gérer les sujets</h3>
                <p>Ajouter ou supprimer des sujets.</p>
                <button class="btn btn-primary" onclick="window.location.href='/dashboard/subjects'">Gérer des sujets</button>
            </div>
        </c:if>
    </section>
</body>
</html>
