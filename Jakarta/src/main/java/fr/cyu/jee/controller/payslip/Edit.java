package fr.cyu.jee.controller.payslip;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.beans.Payslip;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.PayslipDAO;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payslip/edit/*")
public class Edit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

        req.setAttribute("id", idStr);

        try {
            PayslipDAO pdao = new PayslipDAO();
            UserDAO udao = new UserDAO();

            Payslip payslip = pdao.get(Integer.parseInt(idStr));
            List<User> users = udao.getAll();

            req.setAttribute("payslip", payslip);
            req.setAttribute("users", users);
        } catch (Exception e) {
        }

        req.getRequestDispatcher("/payslip/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

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

        UserDAO udao = new UserDAO();
        User user;

        try {
            user = udao.get(Integer.parseInt(userIdStr));
        } catch (Exception e) {
            req.setAttribute("error", "Employé inconnu");
            doGet(req, resp);
            return;
        }

        PayslipDAO pdao = new PayslipDAO();
        int id;

        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            req.setAttribute("error", "Fiche de paie inconnue");
            doGet(req, resp);
            return;
        }

        Payslip payslip = pdao.get(id);
        if (payslip == null) {
            req.setAttribute("error", "Fiche de paie inconnue");
            doGet(req, resp);
            return;
        }

        try {
            payslip.setUser(user);
            payslip.setHour(Float.parseFloat(hourStr));
            payslip.setWage(Float.parseFloat(wageStr));
            payslip.setBonus(bonusStr.isEmpty() ? 0 : Float.parseFloat(bonusStr));
            payslip.setMalus(malusStr.isEmpty() ? 0 : Float.parseFloat(malusStr));
            payslip.setDate(Timestamp.valueOf(dateStr + " 00:00:00"));

            HibernateUtil.update(payslip);

            resp.sendRedirect(req.getContextPath() + "/payslip");
        } catch (Exception e) {
            req.setAttribute("error", "Erreur lors de la modification: " + e.getMessage());
            doGet(req, resp);
        }
    }
}
