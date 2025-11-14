<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Erreur 404</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
    <jsp:include page="/components/header.jsp" />
    <h1>La page demandée est inconnue.</h1>
    <a href="/">Retourner en lieu sur.</a>
    <jsp:include page="/components/footer.jsp" />
</body>

</html>