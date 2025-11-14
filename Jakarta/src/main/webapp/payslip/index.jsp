<%@ page import="java.util.Collection" %>
<%@ page import="fr.cyu.jee.beans.Payslip" %>
<%@ page import="fr.cyu.jee.beans.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Fiches de paie</title>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<jsp:include page="/components/header.jsp"/>
<div class="main">
    <div>
        <form class="inline-form" method="post" action="${pageContext.request.contextPath}/payslip">
            <label for="user_id">Employé</label>
            <select id="user_id" name="user_id" required>
                <option value="">--Sélectionner un employé--</option>
                <%
                    List<User> users = (List<User>) request.getAttribute("users");
                    if (users != null) {
                        for (User u : users) {
                %>
                <option value="<%= u.getId()%>"><%= u.getFirstLastName()%></option>
                <%
                        }
                    }
                %>
            </select>

            <label for="date">Date (mois de paie)</label>
            <input type="date" id="date" name="date" required>

            <label for="hour">Heures travaillées</label>
            <input type="number" step="0.01" id="hour" name="hour" required placeholder="Ex: 151.67">

            <label for="wage">Taux horaire (€)</label>
            <input type="number" step="0.01" id="wage" name="wage" required placeholder="Ex: 15.50">

            <label for="bonus">Primes (€)</label>
            <input type="number" step="0.01" id="bonus" name="bonus" value="0" placeholder="Ex: 200.00">

            <label for="malus">Déductions (€)</label>
            <input type="number" step="0.01" id="malus" name="malus" value="0" placeholder="Ex: 50.00">

            <button type="submit">Ajouter</button>
            <label style="color: red">${pageContext.request.getAttribute("error") == null ? "" :
                pageContext.request.getAttribute("error")}</label>
            <label style="color: green">${pageContext.request.getAttribute("success") == null ? "" :
                pageContext.request.getAttribute("success")}</label>
        </form>
    </div>

    <div>
        <h2>Rechercher des fiches de paie</h2>
        <form class="inline-form" method="get" action="${pageContext.request.contextPath}/payslip">
            <label for="filter_user_id">Employé</label>
            <select id="filter_user_id" name="user_id">
                <option value="">--Tous les employés--</option>
                <%
                    if (users != null) {
                        String selectedUserId = request.getParameter("user_id");
                        for (User u : users) {
                            boolean selected = selectedUserId != null && selectedUserId.equals(String.valueOf(u.getId()));
                %>
                <option value="<%= u.getId()%>" <%= selected ? "selected" : ""%>><%= u.getFirstLastName()%></option>
                <%
                        }
                    }
                %>
            </select>

            <label for="month">Mois</label>
            <select id="month" name="month">
                <option value="">--Tous les mois--</option>
                <%
                    String selectedMonth = request.getParameter("month");
                    String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", 
                                      "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
                    for (int i = 1; i <= 12; i++) {
                        boolean selected = selectedMonth != null && selectedMonth.equals(String.valueOf(i));
                %>
                <option value="<%= i%>" <%= selected ? "selected" : ""%>><%= months[i-1]%></option>
                <%
                    }
                %>
            </select>

            <label for="year">Année</label>
            <select id="year" name="year">
                <option value="">--Toutes les années--</option>
                <%
                    String selectedYear = request.getParameter("year");
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    for (int y = currentYear; y >= currentYear - 5; y--) {
                        boolean selected = selectedYear != null && selectedYear.equals(String.valueOf(y));
                %>
                <option value="<%= y%>" <%= selected ? "selected" : ""%>><%= y%></option>
                <%
                    }
                %>
            </select>

            <button type="submit">Filtrer</button>
            <a href="${pageContext.request.contextPath}/payslip">
                <button type="button">Réinitialiser</button>
            </a>
        </form>
    </div>

    <div>
        <h1>Fiches de paie</h1>
        <table>
            <caption>
                <%
                    Collection<Payslip> payslips = (Collection<Payslip>) request.getAttribute("payslips");
                    int count = payslips != null ? payslips.size() : 0;
                %>
                Liste des fiches de paie (<%= count%>)
            </caption>
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Employé</th>
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
                if (payslips != null && !payslips.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    for (Payslip p : payslips) {
            %>
            <tr>
                <th scope="row"><%= p.getId()%></th>
                <td><%= p.getUser().getFirstLastName()%></td>
                <td><%= sdf.format(p.getDate())%></td>
                <td><%= String.format("%.2f", p.getHour())%></td>
                <td><%= String.format("%.2f €", p.getWage())%></td>
                <td><%= String.format("%.2f €", p.getHour() * p.getWage())%></td>
                <td><%= String.format("%.2f €", p.getBonus())%></td>
                <td><%= String.format("%.2f €", p.getMalus())%></td>
                <td><strong><%= String.format("%.2f €", p.getTotal())%></strong></td>
                <td>
                    <div class="action-menu">
                        <a href="${pageContext.request.contextPath}/payslip/view/<%= p.getUser().getId()%>">
                            <button type="button">Voir tout</button>
                        </a>
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
                } else {
            %>
            <tr>
                <td colspan="10" style="text-align: center;">Aucune fiche de paie trouvée</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/components/footer.jsp"/>
</body>

</html>