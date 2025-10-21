<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <title>Ajout projet</title>
    </head>

    <body>
        <h1>Enregistrez les informations du projet</h1>

        <form method="post" action="${pageContext.request.contextPath}/form">
            <label for="project_name">Nom</label>
            <input type="text" id="project_name" name="project_name" required><br>
            <div class="checkbox-projet">
                <label for="ongoing">En cours</label>
                <input type="checkbox" id="ongoing" name="ongoing" required>
                <label for="finished">Terminé</label>
                <input type="checkbox" id="finished" name="finished" required>
                <label for="canceled">Annulé</label>
                <input type="checkbox" id="canceled" name="canceled" required>
            </div>
            <input type="submit" value="Enregistrer">
        </form>
    </body>

    </html>