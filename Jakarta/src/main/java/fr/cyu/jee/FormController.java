package fr.cyu.jee;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/")
public class FormController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // String firstName = request.getParameter("first_name");
        // String lastName = request.getParameter("last_name");
        // String sexInput = request.getParameter("sex");
        // String[] transportInput = request.getParameterValues("transport");
        // String postCodeInput = request.getParameter("post_code");

        // if(firstName == null || lastName == null || sexInput == null ||
        // transportInput == null || postCodeInput == null
        // || firstName.isBlank() || lastName.isBlank() || sexInput.isBlank() ||
        // postCodeInput.isBlank()) {
        // request.setAttribute("error", "Valeurs manquantes. Essayez de nouveau");
        // request.getRequestDispatcher("/formulaire.jsp").forward(request, response);
        // } else {
        // Sex sex;
        // Set<Transport> transports;
        // int postCode;
        // try {
        // sex = Sex.valueOf(sexInput.toUpperCase());
        // transports = Arrays
        // .stream(transportInput)
        // .map(t -> Transport.valueOf(t.toUpperCase()))
        // .collect(Collectors.toSet());
        // postCode = Integer.parseInt(postCodeInput);
        // } catch (IllegalArgumentException e) {
        // request.setAttribute("error", "Certains champs sont invalides: " +
        // e.getMessage());
        // request.getRequestDispatcher("/formulaire.jsp").forward(request, response);
        // return;
        // }

        // Profile profile = new Profile();
        // profile.setFirstName(firstName);
        // profile.setLastName(lastName);
        // profile.setSex(sex);
        // profile.setTransports(transports);
        // profile.setPostCode(postCode);

        // try (Session currentSession =
        // HibernateUtil.getSessionFactory().openSession()) {
        // currentSession.getTransaction().begin();
        // currentSession.persist(profile);
        // currentSession.getTransaction().commit();
        // }

        // request.setAttribute("profile", profile);
        // request.getRequestDispatcher("/resultat.jsp").forward(request, response);
        // }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, res);
    }
}
