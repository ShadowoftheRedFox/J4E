<%@ page import="fr.cyu.jee.beans.Payslip" %>
<%@ page import="fr.cyu.jee.beans.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <title>Fiche de paie - PDF</title>
    <style>
        @media print {
            .no-print {
                display: none;
            }
            body {
                margin: 0;
                padding: 20px;
            }
        }
        
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
        }
        
        .payslip-container {
            border: 2px solid #333;
            padding: 30px;
            background-color: white;
        }
        
        .header {
            text-align: center;
            border-bottom: 3px solid #333;
            padding-bottom: 20px;
            margin-bottom: 30px;
        }
        
        .header h1 {
            margin: 0;
            font-size: 28px;
            color: #333;
        }
        
        .header p {
            margin: 5px 0;
            color: #666;
        }
        
        .section {
            margin-bottom: 25px;
        }
        
        .section-title {
            background-color: #f0f0f0;
            padding: 10px;
            font-weight: bold;
            font-size: 16px;
            border-left: 4px solid #333;
            margin-bottom: 15px;
        }
        
        .info-grid {
            display: grid;
            grid-template-columns: 200px 1fr;
            gap: 10px;
            margin-bottom: 10px;
        }
        
        .info-label {
            font-weight: bold;
            color: #555;
        }
        
        .info-value {
            color: #333;
        }
        
        .calculation-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        .calculation-table th,
        .calculation-table td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }
        
        .calculation-table th {
            background-color: #f5f5f5;
            font-weight: bold;
        }
        
        .calculation-table .amount {
            text-align: right;
            font-family: 'Courier New', monospace;
        }
        
        .total-row {
            background-color: #e8f4f8 !important;
            font-weight: bold;
            font-size: 18px;
        }
        
        .total-row td {
            border-top: 3px solid #333;
        }
        
        .footer {
            margin-top: 50px;
            padding-top: 20px;
            border-top: 2px solid #ddd;
            text-align: center;
            color: #666;
            font-size: 12px;
        }
        
        .signature-section {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 40px;
            margin-top: 60px;
        }
        
        .signature-box {
            text-align: center;
        }
        
        .signature-line {
            border-top: 1px solid #333;
            margin-top: 60px;
            padding-top: 5px;
        }
        
        .no-print {
            text-align: center;
            margin: 20px 0;
        }
        
        .no-print button {
            padding: 12px 30px;
            font-size: 16px;
            cursor: pointer;
            background-color: #333;
            color: white;
            border: none;
            border-radius: 4px;
            margin: 0 10px;
        }
        
        .no-print button:hover {
            background-color: #555;
        }
    </style>
</head>

<body>
<%
    Payslip payslip = (Payslip) request.getAttribute("payslip");
    if (payslip == null) {
%>
<div class="payslip-container">
    <p style="text-align: center; color: red; font-size: 18px;">Fiche de paie introuvable</p>
</div>
<div class="no-print">
    <a href="${pageContext.request.contextPath}/payslip">
        <button>Retour à la liste</button>
    </a>
</div>
<%
} else {
    User user = payslip.getUser();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat monthYear = new SimpleDateFormat("MMMM yyyy");
%>

<div class="no-print">
    <button onclick="window.print()">Imprimer / Enregistrer en PDF</button>
    <a href="${pageContext.request.contextPath}/payslip">
        <button>Retour à la liste</button>
    </a>
</div>

<div class="payslip-container">
    <div class="header">
        <h1>FICHE DE PAIE</h1>
        <p>Période: <%= monthYear.format(payslip.getDate())%></p>
        <p>Document généré le <%= sdf.format(new java.util.Date())%></p>
    </div>
    
    <div class="section">
        <div class="section-title">INFORMATIONS ENTREPRISE</div>
        <div class="info-grid">
            <div class="info-label">Raison sociale:</div>
            <div class="info-value">Entreprise JEE - GSI3</div>
            
            <div class="info-label">Département:</div>
            <div class="info-value"><%= user.getDepartment() != null ? user.getDepartment().getName() : "Non assigné"%></div>
        </div>
    </div>
    
    <div class="section">
        <div class="section-title">INFORMATIONS SALARIÉ</div>
        <div class="info-grid">
            <div class="info-label">Nom complet:</div>
            <div class="info-value"><%= user.getFirstLastName()%></div>
            
            <div class="info-label">Identifiant:</div>
            <div class="info-value">#<%= user.getId()%></div>
            
            <div class="info-label">Département:</div>
            <div class="info-value"><%= user.getDepartment() != null ? user.getDepartment().getName() : "Non assigné"%></div>
        </div>
    </div>
    
    <div class="section">
        <div class="section-title">DÉTAIL DES RÉMUNÉRATIONS</div>
        
        <table class="calculation-table">
            <thead>
            <tr>
                <th>Libellé</th>
                <th style="text-align: center;">Base</th>
                <th style="text-align: center;">Taux/Nombre</th>
                <th class="amount">Montant</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Salaire de base</td>
                <td style="text-align: center;"><%= String.format("%.2f h", payslip.getHour())%></td>
                <td style="text-align: center;"><%= String.format("%.2f €/h", payslip.getWage())%></td>
                <td class="amount"><%= String.format("%.2f €", payslip.getHour() * payslip.getWage())%></td>
            </tr>
            
            <% if (payslip.getBonus() > 0) { %>
            <tr>
                <td>Primes et gratifications</td>
                <td style="text-align: center;">-</td>
                <td style="text-align: center;">-</td>
                <td class="amount"><%= String.format("%.2f €", payslip.getBonus())%></td>
            </tr>
            <% } %>
            
            <tr style="background-color: #f9f9f9;">
                <td colspan="3" style="text-align: right; font-weight: bold;">TOTAL BRUT</td>
                <td class="amount" style="font-weight: bold;">
                    <%= String.format("%.2f €", payslip.getHour() * payslip.getWage() + payslip.getBonus())%>
                </td>
            </tr>
            </tbody>
        </table>
        
        <% if (payslip.getMalus() > 0) { %>
        <table class="calculation-table" style="margin-top: 20px;">
            <thead>
            <tr>
                <th colspan="3">DÉDUCTIONS</th>
                <th class="amount">Montant</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td colspan="3">Retenues et déductions</td>
                <td class="amount"><%= String.format("%.2f €", payslip.getMalus())%></td>
            </tr>
            <tr style="background-color: #f9f9f9;">
                <td colspan="3" style="text-align: right; font-weight: bold;">TOTAL DÉDUCTIONS</td>
                <td class="amount" style="font-weight: bold;"><%= String.format("%.2f €", payslip.getMalus())%></td>
            </tr>
            </tbody>
        </table>
        <% } %>
        
        <table class="calculation-table" style="margin-top: 20px;">
            <tbody>
            <tr class="total-row">
                <td colspan="3" style="text-align: right;">NET À PAYER</td>
                <td class="amount"><%= String.format("%.2f €", payslip.getTotal())%></td>
            </tr>
            </tbody>
        </table>
    </div>
    
    <div class="signature-section">
        <div class="signature-box">
            <p><strong>L'Employeur</strong></p>
            <div class="signature-line">Signature et cachet</div>
        </div>
        <div class="signature-box">
            <p><strong>Le Salarié</strong></p>
            <div class="signature-line">Signature</div>
        </div>
    </div>
    
    <div class="footer">
        <p>Bulletin de paie à conserver sans limitation de durée</p>
        <p>En cas de contestation, s'adresser au service des ressources humaines</p>
        <p style="margin-top: 10px; font-size: 10px;">
            Document généré automatiquement - ID Fiche: #<%= payslip.getId()%>
        </p>
    </div>
</div>

<div class="no-print" style="margin-top: 20px;">
    <button onclick="window.print()">Imprimer / Enregistrer en PDF</button>
    <a href="${pageContext.request.contextPath}/payslip/view/<%= user.getId()%>">
        <button>Voir toutes les fiches de <%= user.getFirstName()%></button>
    </a>
    <a href="${pageContext.request.contextPath}/payslip">
        <button>Retour à la liste</button>
    </a>
</div>

<%
    }
%>
</body>

</html>
