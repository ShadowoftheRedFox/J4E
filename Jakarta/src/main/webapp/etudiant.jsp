<%@ page import="fr.cyu.td6.beans.Etudiant" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: scp-079
  Date: 10/12/25
  Time: 5:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        table * {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<h1>Effacer un étudiant</h1>
<form method="post" action="${pageContext.request.contextPath}/etudiant">
    <label>Id</label><input type="number" name="id" min="0" required />
    <input type="text" name="delete" value="true" style="display: none;">
    <input type="submit" value="Effacer">
</form>
<h1>Enregistrez vos informations</h1>
<label style="color: red">${pageContext.request.getAttribute("error") == null ? "" : pageContext.request.getAttribute("error")}</label>
<form method="post" action="${pageContext.request.contextPath}/etudiant">
    <label>Nom</label><input name="nom" type="text" required><br>
    <label>Prénom</label><input name="prenom" type="text" required><br>
    <label>Age</label><input name="age" type="number" step="1" min="0" max="99999" required><br>
    <input type="text" name="delete" value="false" style="display: none;">
    <input type="submit" value="Enregistrer">
</form>
<table>
    <thead>
        <tr>
            <th>Id</th>
            <th>Prénom</th>
            <th>Nom</th>
            <th>Age</th>
        </tr>
    </thead>
    <tbody>
        <%
            List<Etudiant> etu = ((List<Etudiant>)request.getAttribute("etudiants"));
            if (etu != null) {
                for (Etudiant etudiant : etu) {
        %>
            <tr>
                <th><%= etudiant.getId()%> </th>
                <td><%= etudiant.getPrenom()%></td>
                <td><%= etudiant.getNom()%></td>
                <td><%= etudiant.getAge()%></td>
            </tr>
        <%
                }
            }
        %>
    </tbody>
</table>
</body>
</html>
