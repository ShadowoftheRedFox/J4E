<%@ page import="org.atilla.cytraining.user.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>CY Training - Création d'exercices</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        .container {
            width: 50%;
            margin: auto;
            background: white;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            margin-top: 20px;
            text-align: left;
        }
        h2 {
            color: #004080;
        }
        label {
            font-weight: bold;
        }
        input, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            align-items: center;
        }
        .response-item {
            margin-bottom: 10px;
        }
        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 15px 15px;
            cursor: pointer;
            border-radius: 5px;
            font-weight: bold;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<body>
    <div class="container">
        <h2>Créer une question</h2>
        <form action="${pageContext.request.contextPath}/mcq/add" method="post">
            <input type="hidden" name="userId" value="${user.getId()}" />
            
            <label for="title">Titre :</label>
            <input type="text" id="title" name="title" required><br>

            <label for="description">Description :</label>
            <textarea id="description" name="description" required></textarea><br>

            <h3>Réponses :</h3>
            <div id="responses-container">
                <div class="response-item">
                    <input type="text" name="responses[0].answer" placeholder="Réponse 1" required>
                    <label>
                        <input type="checkbox" name="responses[0].correct" value="true"> Correct
                    </label>
                </div>
            </div>
            <button type="button" onclick="addResponse()">Ajouter une réponse</button>
            <button type="submit">Créer la question</button>
        </form>
    </div>
</body>
<script>
    function addResponse() {
        let index = document.getElementById("responses-container").children.length;
        if (index < 10) {
            const container = document.getElementById("responses-container");
            const div = document.createElement("div");
    
            div.innerHTML = `<div class="response-item">
                <input type="text" name="responses[:index].answer" placeholder="Réponse :mindex" required>
                <label>
                    <input type="checkbox" name="responses[:index].correct" value="true"> Correct
                </label>
            </div>`.replaceAll(":index", index).replaceAll(":mindex", index+1);
    
            container.appendChild(div);
        } else {
            alert("Vous ne pouvez ajouter que 10 réponses maximum.");
        }
    }
    </script>
</html>