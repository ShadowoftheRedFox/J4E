<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header id="header">
    <!-- TODO heck les perm si ils peuvent avoir accès aux trucs -->
    <a href="${pageContext.request.contextPath}/">
        <h1>Header</h1>
    </a>
    <a href="${pageContext.request.contextPath}/employee">Employés</a>
    <a href="${pageContext.request.contextPath}/department">Départements</a>
    <a href="${pageContext.request.contextPath}/project">Projets</a>
    <a href="${pageContext.request.contextPath}/report">Rapport</a>
    <a href="${pageContext.request.contextPath}/payslip">Fiches de paies</a>
    <%
        boolean isConnected = false;
        Object strId = request.getSession().getAttribute("user");
        if (strId != null) {
            isConnected = true;
        }

        for (Cookie c : request.getCookies()) {
            if (c.getName() == "account") {
                isConnected = true;
                break;
            }
        }
        if (!isConnected) {
    %>
    <a href="${pageContext.request.contextPath}/auth/login">Se connecter</a>
    <%
    } else {
    %>
    <a href="${pageContext.request.contextPath}/auth/logout">Se déconnecter</a>
    <%
        }
    %>
</header>