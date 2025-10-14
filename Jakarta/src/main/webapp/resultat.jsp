<%-- Created by IntelliJ IDEA. User: scp-079 Date: 9/30/25 Time: 6:16 PM To change this template use File | Settings |
    File Templates. --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@ page import="fr.cyu.jee.beans.Profile" %>
            <%@ page import="fr.cyu.jee.beans.Transport" %>
                <%@ page import="java.util.List" %>
                    <%@ page import="java.util.ArrayList" %>
                        <html>

                        <head>
                            <title>Exercice 1, partie 2</title>
                        </head>

                        <body>
                            <h1>Résultat:</h1>
                            <table>
                                <tr>
                                    <th scope="row">Nom</th>
                                    <td>
                                        <%= ((Profile)request.getAttribute("profile")).getLastName()%>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">Prénom</th>
                                    <td>
                                        <%= ((Profile)request.getAttribute("profile")).getFirstName()%>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row">Sexe</th>
                                    <td>
                                        <%= ((Profile)request.getAttribute("profile")).getSex().getName()%>
                                    </td>
                                </tr>
                                <% List<Transport> transports = new ArrayList<>
                                        (((Profile)request.getAttribute("profile")).getTransports());
                                        out.write("<tr>");
                                            out.write("<th scope=\"row\">Transports</th>");
                                            out.write("
                                        <tr>");
                                            out.write("<td>" + transports.getFirst().getName() + "</td>");
                                            out.write("</tr>");

                                        for(int i = 1; i < transports.size(); i++) { out.write("<tr>");
                                            out.write("<th></th>");
                                            out.write("<td>" + transports.get(i).getName() + "</td>");
                                            out.write("</tr>");
                                            }
                                            %>
                                            <tr>
                                                <th scope="row">Code postal</th>
                                                <td>
                                                    <%= ((Profile)request.getAttribute("profile")).getPostCode()%>
                                                </td>
                                            </tr>
                            </table>
                        </body>

                        </html>