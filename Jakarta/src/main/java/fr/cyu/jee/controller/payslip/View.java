package fr.cyu.jee.controller.payslip;

import java.io.IOException;
import java.util.List;

import fr.cyu.jee.beans.Payslip;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.PayslipDAO;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payslip/view/*")
public class View extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] paths = req.getPathInfo().split("/");
        String idStr = "x";
        if (paths.length > 1) {
            idStr = paths[1];
        }

        try {
            int userId = Integer.parseInt(idStr);
            UserDAO udao = new UserDAO();
            PayslipDAO pdao = new PayslipDAO();

            User user = udao.get(userId);
            List<Payslip> payslips = pdao.getByUser(userId);

            req.setAttribute("user", user);
            req.setAttribute("payslips", payslips);
        } catch (Exception e) {
        }

        req.getRequestDispatcher("/payslip/view.jsp").forward(req, resp);
    }
}
