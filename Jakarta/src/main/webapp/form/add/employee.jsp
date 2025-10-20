<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Ajouter un employé</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
    <jsp:include page="/components/header.jsp" />
    <h1>Enregistrez vos informations</h1>
    <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
        pageContext.request.getAttribute("error")}</label>
    <form method="post" action="${pageContext.request.contextPath}/form">
        <label for="last_name">Nom</label><input name="last_name" id="last_name" type="text" required>
        <label for="first_name">Prénom</label><input name="first_name" id="first_name" type="text" required>
        <hr />
        <p>Sexe</p>
        <label for="sex_male">Homme</label><input name="sex" id="sex_male" type="radio" value="male" required>
        <label for="sex_female">Femme</label><input name="sex" id="sex_female" type="radio" value="female" required>
        <label for="sex_other">Autre</label><input name="sex" id="sex_other" type="radio" value="other" required>
        <label for="matricule">Matricule</label><input name="matricule" id="matricule" type="number" placeholder="4444" required min="0">
        <label for="département">Département</label><input name="département" id="département" type="number" placeholder="7777" required min="0">
        <button type="submit">Enregistrer</button>
    </form>
    <jsp:include page="/components/footer.jsp" />
</body>

</html>