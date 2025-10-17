<%-- Created by IntelliJ IDEA. User: scp-079 Date: 9/30/25 Time: 5:54 PM To change this template use File | Settings |
    File Templates. --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <html>

        <head>
            <title>Exercice 1</title>
        </head>

        <body>
            <h1>Enregistrez vos informations</h1>
            <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
                pageContext.request.getAttribute("error")}</label>
            <form method="post" action="${pageContext.request.contextPath}/form">
                <label>Nom</label><input name="last_name" type="text" required><br>
                <label>Prénom</label><input name="first_name" type="text" required><br>
                <label>Sexe</label>
                <label>Homme</label><input name="sex" type="radio" value="male" required>
                <label>Femme</label><input name="sex" type="radio" value="female" required>
                <label>Autre</label><input name="sex" type="radio" value="other" required><br>
                <label>Matricule</label><input name="matricule" type="text" value="matricule" required>
                <label>Département</label><input name="département" type="text" value="departement">
                <br>
                <input type="submit" value="Enregistrer">
            </form>


        </body>

        </html>