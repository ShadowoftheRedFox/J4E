<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Connexion</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
    <jsp:include page="/components/header.jsp" />
    <h1>Connexion</h1>
    <form>
        <form method="post" action="${pageContext.request.contextPath}/auth/login">
            <label for="identifier">Identifiant</label>
            <input id="identifier" name="identifier" type="text" required>
            <label for="password">Mot de passe</label>
            <input id="passwordr" name="password" type="password" required>
            <button type="submit">Se connecter</button>
        </form>
    </form>
    <jsp:include page="/components/footer.jsp" />
</body>

</html>