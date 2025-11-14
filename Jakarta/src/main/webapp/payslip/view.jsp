<%@ page import="fr.cyu.jee.beans.Payslip" %>
<%@ page import="fr.cyu.jee.beans.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Fiches de paie par employé</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<jsp:include page="/components/header.jsp"/>
<div class="main">
    <%
        User user = (User) request.getAttribute("user");
        List<Payslip> payslips = (List<Payslip>) request.getAttribute("payslips");
        
        if (user == null) {
    %>
    <div>
        <p>Employé introuvable</p>
    </div>
    <%
    } else {
    %>
    <div>
        <h1>Fiches de paie de <%= user.getFirstLastName()%></h1>
        <p><strong>Département:</strong> <%= user.getDepartment() != null ? user.getDepartment().getName() : "Non assigné"%></p>
        
        <%
            if (payslips != null && !payslips.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                double totalNet = 0;
                for (Payslip p : payslips) {
                    totalNet += p.getTotal();
                }
        %>
        
        <div style="background-color: #f0f0f0; padding: 15px; margin: 20px 0; border-radius: 5px;">
            <h3>Récapitulatif</h3>
            <p><strong>Nombre de fiches:</strong> <%= payslips.size()%></p>
            <p><strong>Total net versé:</strong> <%= String.format("%.2f €", totalNet)%></p>
            <p><strong>Moyenne par fiche:</strong> <%= String.format("%.2f €", totalNet / payslips.size())%></p>
        </div>
        
        <table>
            <caption>Historique des fiches de paie (<%= payslips.size()%>)</caption>
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Date</th>
                <th scope="col">Heures</th>
                <th scope="col">Taux horaire</th>
                <th scope="col">Salaire de base</th>
                <th scope="col">Primes</th>
                <th scope="col">Déductions</th>
                <th scope="col">Net à payer</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Payslip p : payslips) {
            %>
            <tr>
                <th scope="row"><%= p.getId()%></th>
                <td><%= sdf.format(p.getDate())%></td>
                <td><%= String.format("%.2f", p.getHour())%></td>
                <td><%= String.format("%.2f €", p.getWage())%></td>
                <td><%= String.format("%.2f €", p.getHour() * p.getWage())%></td>
                <td><%= String.format("%.2f €", p.getBonus())%></td>
                <td><%= String.format("%.2f €", p.getMalus())%></td>
                <td><strong><%= String.format("%.2f €", p.getTotal())%></strong></td>
                <td>
                    <div class="action-menu">
                        <a href="${pageContext.request.contextPath}/payslip/pdf/<%= p.getId()%>">
                            <button type="button">PDF</button>
                        </a>
                        <form method="post" action="${pageContext.request.contextPath}/payslip/delete">
                            <input type="number" name="id" value="<%= p.getId()%>">
                            <button type="submit">Effacer</button>
                        </form>
                        <a href="${pageContext.request.contextPath}/payslip/edit/<%= p.getId()%>">
                            <button>Modifier</button>
                        </a>
                    </div>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <%
            } else {
        %>
        <div style="text-align: center; padding: 40px;">
            <p>Aucune fiche de paie trouvée pour cet employé.</p>
        </div>
        <%
            }
        %>
    </div>
    <% } %>
    
    <a href="${pageContext.request.contextPath}/payslip">Retour à la liste</a>
</div>
<jsp:include page="/components/footer.jsp"/>
</body>

</html>
