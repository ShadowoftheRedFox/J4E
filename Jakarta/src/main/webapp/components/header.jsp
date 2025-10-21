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
    <!-- TODO si connecter, afficher logout -->
    <a href="${pageContext.request.contextPath}/auth/login">Se connecter</a>
</header>