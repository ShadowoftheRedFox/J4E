<%@ page import="org.atilla.cytraining.util.JSPUtils" %>
<%@ page import="java.util.Optional" %>
<%@ page import="jakarta.servlet.jsp.JspWriter" %>
<%@ page import="org.atilla.cytraining.user.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>CY Training - Se connecter</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
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
            <img src="/img/user.png" alt="user_icon">
            <label><%= user.getUsername() %></label>
        </div>
    <% } %>
</nav>
<div id="main">
    <div id="forms">
        <form action="${pageContext.request.contextPath}/auth/login" method="post">
            <label class="form_title">Connexion</label>
            <fieldset>
                <label for="login_username">Nom d'utilisateur:</label>
                <div>
                    <input class="<%=JSPUtils.errorInputClass(pageContext, "error_username", "error_login")%>"
                           type="text" id="login_username" name="username">
                    <% JSPUtils.errorSpan(pageContext, "error_username", "error_login"); %>
                </div>
                <label for="login_password">Mot de passe:</label>
                <div>
                    <input class="<%= JSPUtils.errorInputClass(pageContext, "error_password", "error_login")%>"
                           type="password" id="login_password" name="password" minlength="8" maxlength="64">
                    <% JSPUtils.errorSpan(pageContext, "error_password", "error_login"); %>
                </div>
            </fieldset>
            <input type="submit" value="Se connecter">
        </form>
        <form action="${pageContext.request.contextPath}/auth/register" method="post">
            <label class="form_title">Inscription</label>
            <fieldset>
                <label for="register_username">Nom d'utilisateur:</label>
                <input class="<%=JSPUtils.errorInputClass(pageContext, "error_username")%>" type="text"
                       id="register_username" name="username">
                <% JSPUtils.errorSpan(pageContext, "error_username"); %>
                <label for="register_password">Mot de passe:</label>
                <input class="<%= JSPUtils.errorInputClass(pageContext, "error_password") %>" type="password"
                       id="register_password" name="password" name="password" minlength="8" maxlength="64">
                <% JSPUtils.errorSpan(pageContext, "error_password"); %>
                <label for="register_password_confirm">Mot de passe:</label>
                <input class="<%= JSPUtils.errorInputClass(pageContext, "error_password") %>" type="password"
                       id="register_password_confirm" name="passwordConfirm" name="password" minlength="8" maxlength="64">
                <% JSPUtils.errorSpan(pageContext, "error_password"); %>

                <label for="is_creator">Êtes-vous un créateur ?</label>
                <input type="checkbox" id="is_creator" name="creator" checked=false value="true">

            </fieldset>
            <input type="submit" value="S'inscrire">
        </form>
    </div>
</div>
</body>
</html>
