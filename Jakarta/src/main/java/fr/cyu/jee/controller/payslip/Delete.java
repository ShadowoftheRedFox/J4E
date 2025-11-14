package fr.cyu.jee.controller.payslip;

import fr.cyu.jee.dao.PayslipDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/payslip/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/payslip");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id;

        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            doGet(req, resp);
            return;
        }

        PayslipDAO pdao = new PayslipDAO();
        pdao.delete(id);
        doGet(req, resp);
    }
}
