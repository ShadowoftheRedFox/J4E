<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Ajout departement</title>
</head>

<body>
    <h1>Enregistrez les informations du département</h1>

    <form method="post" action="${pageContext.request.contextPath}/form">
        <label>Nom</label><input name="department_name" type="text" required><br>
        <input type="submit" value="Enregistrer">
    </form>
</body>

</html>