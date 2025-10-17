<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <title>Ajout projet</title>
    </head>

    <body>
        <h1>Enregistrez les informations du projet</h1>

        <form method="post" action="${pageContext.request.contextPath}/form">
            <label>Nom</label><input name="project_name" type="text" required><br>
            <input type="submit" value="Enregistrer">
        </form>
    </body>

    </html>