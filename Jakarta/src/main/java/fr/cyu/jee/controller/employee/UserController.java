package fr.cyu.jee.controller.employee;

import java.io.IOException;

import fr.cyu.jee.beans.User;
import fr.cyu.jee.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() {
        userDao = new UserDAO();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String name = request.getParameter("last_name");
        String firstName = request.getParameter("first_name");
        String matricule = request.getParameter("matricule");
        String department = request.getParameter("departement");

        if (name == null || firstName == null || matricule == null ||
                department == null || name.isEmpty() || firstName.isEmpty() ||
                matricule.isEmpty() || department.isEmpty()) {
            request.setAttribute("error", "Remplit tout les champs ");
            request.getRequestDispatcher("employee.jsp").forward(request, response);
            return;
        }

        // FIXME
        // User user = new User(name, firstName, Integer.parseInt(matricule),
        // department);
        User user = new User();

        try {
            // Sauvegarde en base
            // userDao.create(user);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        // Envoyer vers la vue
        // request.setAttribute("person", user);
        request.getRequestDispatcher("resultat.jsp").forward(request, response);
    }

}
