<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Déconnexion</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
    <jsp:include page="/components/header.jsp" />
    <h1>Déconnexion</h1>
    <form>
        <form method="post" action="${pageContext.request.contextPath}/auth/logout">
            <button type="submit">Se déconnecter</button>
        </form>
    </form>
    <jsp:include page="/components/footer.jsp" />
</body>

</html>