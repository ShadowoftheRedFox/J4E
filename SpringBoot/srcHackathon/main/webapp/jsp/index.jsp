<%@ page import="org.atilla.cytraining.user.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <title>CY Training - Menu Principal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>

<body>
    <nav>
        <label id="logo" onclick="window.location='/'">CY Training</label>
        <a href="${pageContext.request.contextPath}/login">Se connecter / S'enregistrer</a>
        <%
            User user = (User) session.getAttribute("user");
            if(user != null) {
        %>
        <div id="user_nav">
            <% if (user.isAdmin() || user.isCreator() || user.isGloballyCertified()){%>
                <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/dashboard'">Dashboard</button>
            <%}%>
            <img src="/img/user.png" alt="user_icon">
            <label><%= user.getUsername() %></label>
        </div>
        <% } %>
    </nav>

    <div class="nav-container">
        <div>
            Choisissez votre matière d'entraînement :
            <div class="course-container">
                <c:forEach var="subject" items="${subjects}">
                    <button>${subject.name}</button>
                </c:forEach>
            </div>
        </div>
        <% if(user != null) { %>
            <div>
                <p>Profil<br>
                nom : <%= user.getUsername() %><br>
                créateur : <%= user.isCreator() %><br>
            </div>
        <% } %>
    </div>
</body>