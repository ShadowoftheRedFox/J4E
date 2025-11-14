package fr.cyu.jee.controller.payslip;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.cyu.jee.beans.Payslip;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.PayslipDAO;
import fr.cyu.jee.dao.UserDAO;

@WebServlet("/payslip")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PayslipDAO pdao = new PayslipDAO();
        UserDAO udao = new UserDAO();
        
        String userFilter = req.getParameter("user_id");
        String monthFilter = req.getParameter("month");
        String yearFilter = req.getParameter("year");
        
        Collection<Payslip> payslips;
        
        if (userFilter != null || monthFilter != null || yearFilter != null) {
            Map<String, Object> filters = new HashMap<>();
            
            if (userFilter != null && !userFilter.isEmpty()) {
                try {
                    filters.put("user_id", Integer.parseInt(userFilter));
                } catch (NumberFormatException e) {
                }
            }
            
            if (monthFilter != null && !monthFilter.isEmpty()) {
                try {
                    filters.put("month", Integer.parseInt(monthFilter));
                } catch (NumberFormatException e) {
                }
            }
            
            if (yearFilter != null && !yearFilter.isEmpty()) {
                try {
                    filters.put("year", Integer.parseInt(yearFilter));
                } catch (NumberFormatException e) {
                }
            }
            
            payslips = pdao.find(filters);
        } else {
            payslips = pdao.getAll();
        }
        
        List<User> users = udao.getAll();
        
        req.setAttribute("payslips", payslips);
        req.setAttribute("users", users);
        req.getRequestDispatcher("/payslip/index.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdStr = req.getParameter("user_id");
        String hourStr = req.getParameter("hour");
        String wageStr = req.getParameter("wage");
        String bonusStr = req.getParameter("bonus");
        String malusStr = req.getParameter("malus");
        String dateStr = req.getParameter("date");
        
        if (userIdStr == null || hourStr == null || wageStr == null || 
            bonusStr == null || malusStr == null || dateStr == null ||
            userIdStr.isEmpty() || hourStr.isEmpty() || wageStr.isEmpty() || 
            dateStr.isEmpty()) {
            req.setAttribute("error", "Des champs sont vides");
            doGet(req, resp);
            return;
        }
        
        try {
            int userId = Integer.parseInt(userIdStr);
            
 
            User userRef = new User();
            userRef.setId(userId);
            
            Payslip payslip = new Payslip();
            payslip.setUser(userRef);
            payslip.setHour(Float.parseFloat(hourStr));
            payslip.setWage(Float.parseFloat(wageStr));
            payslip.setBonus(bonusStr.isEmpty() ? 0 : Float.parseFloat(bonusStr));
            payslip.setMalus(malusStr.isEmpty() ? 0 : Float.parseFloat(malusStr));
            payslip.setDate(Timestamp.valueOf(dateStr + " 00:00:00"));
            
            PayslipDAO pdao = new PayslipDAO();
            pdao.create(payslip);
            
            req.setAttribute("success", "Fiche de paie créée avec succès");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Format de nombre invalide");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Format de date invalide (YYYY-MM-DD)");
        } catch (Exception e) {
            req.setAttribute("error", "Erreur lors de la création: " + e.getMessage());
        }
        
        doGet(req, resp);
    }
}
