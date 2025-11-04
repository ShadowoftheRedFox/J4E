package fr.cyu.jee.dao;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.HibernateUtil.SessionWrapper;
import fr.cyu.jee.beans.Payslip;
import fr.cyu.jee.beans.User;

public class PayslipDAO implements DAO<Payslip> {
    @Override
    public boolean delete(int id) {
        return HibernateUtil.remove(id, Payslip.class);
    }

    @Override
    public Collection<Payslip> getAll() {
        Optional<List<Payslip>> res = HibernateUtil.list("Payslip", Payslip.class);
        if (res.isPresent()) {
            return res.get();
        }
        return null;
    }

    @Override
    public Payslip get(int id) {
        return HibernateUtil.get(Payslip.class, id);
    }

    @Override
    public boolean create(Payslip payslip) {
        return HibernateUtil.useSession(new SessionWrapper<Boolean>() {
            @Override
            public Boolean use(Transaction trs, Session session) {
                try {
                    // Si l'user a seulement un ID, récupérer la référence depuis la session
                    if (payslip.getUser() != null && payslip.getUser().getId() != 0) {
                        User userRef = session.getReference(User.class, payslip.getUser().getId());
                        payslip.setUser(userRef);
                    }
                    session.persist(payslip);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
    }

    @Override
    public boolean edit(final Payslip p) {
        return HibernateUtil.update(p);
    }

    @Override
    public List<Payslip> find(final Map<String, Object> filter) {
        List<Payslip> payslips = HibernateUtil.useSession(new SessionWrapper<List<Payslip>>() {
            @Override
            public List<Payslip> use(Transaction trs, Session session) {
                String sql = "from Payslip P where 1=1 ";
                
                if (filter.containsKey("user_id")) {
                    sql += "and P.user.id = :user_id ";
                }
                if (filter.containsKey("month")) {
                    sql += "and MONTH(P.date) = :month ";
                }
                if (filter.containsKey("year")) {
                    sql += "and YEAR(P.date) = :year ";
                }
                
                sql += "order by P.date desc";
                
                SelectionQuery<Payslip> q = session.createSelectionQuery(sql, Payslip.class);
                
                if (filter.containsKey("user_id")) {
                    q.setParameter("user_id", filter.get("user_id"));
                }
                if (filter.containsKey("month")) {
                    q.setParameter("month", filter.get("month"));
                }
                if (filter.containsKey("year")) {
                    q.setParameter("year", filter.get("year"));
                }
                
                return q.getResultList();
            }
        });
        
        return payslips;
    }
    
    public List<Payslip> getByUser(final int userId) {
        return HibernateUtil.useSession(new SessionWrapper<List<Payslip>>() {
            @Override
            public List<Payslip> use(Transaction trs, Session session) {
                String sql = "from Payslip P where P.user.id = :user_id order by P.date desc";
                SelectionQuery<Payslip> q = session.createSelectionQuery(sql, Payslip.class);
                q.setParameter("user_id", userId);
                return q.getResultList();
            }
        });
    }
    
    public byte[] generate(Payslip payslip) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            
            User user = payslip.getUser();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            document.add(new Paragraph("FICHE DE PAIE"));
            document.add(new Paragraph("Date: " + sdf.format(payslip.getDate())));
            document.add(new Paragraph("Employe: " + user.getFirstName() + " " + user.getLastName()));
            document.add(new Paragraph("Salaire de base: " + String.format("%.2f EUR", payslip.getHour() * payslip.getWage())));
            
            if (payslip.getBonus() > 0) {
                document.add(new Paragraph("Primes: " + String.format("%.2f EUR", payslip.getBonus())));
            }
            
            if (payslip.getMalus() > 0) {
                document.add(new Paragraph("Deductions: " + String.format("%.2f EUR", payslip.getMalus())));
            }
            
            document.add(new Paragraph("TOTAL: " + String.format("%.2f EUR", payslip.getTotal())));
            
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}