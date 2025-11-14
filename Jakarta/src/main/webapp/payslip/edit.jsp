<%@ page import="fr.cyu.jee.beans.Payslip" %>
<%@ page import="fr.cyu.jee.beans.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Fiches de paie: Modification</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<jsp:include page="/components/header.jsp"/>
<div class="main">
    <%
        Payslip payslip = (Payslip) request.getAttribute("payslip");
        if (payslip == null) {
    %>
    <div>
        <p>Fiche de paie inconnue <%= request.getAttribute("id")%></p>
    </div>
    <%
    } else {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    %>
    <div>
        <form class="inline-form" method="post"
              action="${pageContext.request.contextPath}/payslip/edit/<%= payslip.getId()%>">
            <label for="user_id">Employé</label>
            <select id="user_id" name="user_id" required>
                <%
                    List<User> users = (List<User>) request.getAttribute("users");
                    if (users != null) {
                        for (User u : users) {
                            boolean selected = payslip.getUser().getId() == u.getId();
                %>
                <option value="<%= u.getId()%>" <%= selected ? "selected" : ""%>><%= u.getFirstLastName()%></option>
                <%
                        }
                    }
                %>
            </select>

            <label for="date">Date (mois de paie)</label>
            <input type="date" id="date" name="date" required value="<%= sdf.format(payslip.getDate())%>">

            <label for="hour">Heures travaillées</label>
            <input type="number" step="0.01" id="hour" name="hour" required 
                   value="<%= payslip.getHour()%>" placeholder="Ex: 151.67">

            <label for="wage">Taux horaire (€)</label>
            <input type="number" step="0.01" id="wage" name="wage" required 
                   value="<%= payslip.getWage()%>" placeholder="Ex: 15.50">

            <label for="bonus">Primes (€)</label>
            <input type="number" step="0.01" id="bonus" name="bonus" 
                   value="<%= payslip.getBonus()%>" placeholder="Ex: 200.00">

            <label for="malus">Déductions (€)</label>
            <input type="number" step="0.01" id="malus" name="malus" 
                   value="<%= payslip.getMalus()%>" placeholder="Ex: 50.00">

            <button type="submit">Modifier</button>
            <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
                pageContext.request.getAttribute("error")}</label>
        </form>
    </div>
    <% }%>
    <a href="${pageContext.request.contextPath}/payslip">Retour au menu</a>
</div>
<jsp:include page="/components/footer.jsp"/>
</body>

</html>
