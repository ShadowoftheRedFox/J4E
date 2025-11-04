package fr.cyu.jee.controller.payslip;

import java.io.IOException;
import java.io.OutputStream;

import fr.cyu.jee.beans.Payslip;
import fr.cyu.jee.dao.PayslipDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payslip/pdf/*")
public class Pdf extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

        try {
            int id = Integer.parseInt(idStr);
            PayslipDAO pdao = new PayslipDAO();
            Payslip payslip = pdao.get(id);

            if (payslip == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Fiche de paie introuvable");
                return;
            }

            
            byte[] pdfBytes = pdao.generate(payslip);

            if (pdfBytes == null || pdfBytes.length == 0) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la génération du PDF");
                return;
            }

          
            resp.setContentType("application/pdf");
            resp.setContentLength(pdfBytes.length);
            resp.setHeader("Content-Disposition", 
                "attachment; filename=fiche_paie_" + payslip.getUser().getLastName() + "_" + id + ".pdf");

            
            OutputStream out = resp.getOutputStream();
            out.write(pdfBytes);
            out.flush();
            out.close();

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalide");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Erreur lors de la génération du PDF: " + e.getMessage());
        }
    }
}
