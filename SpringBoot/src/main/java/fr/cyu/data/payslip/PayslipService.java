package fr.cyu.data.payslip;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import fr.cyu.data.employee.Employee;

@Service
public class PayslipService {
    @Autowired
    private PayslipRepository pr;

    public List<Payslip> getAll() {
        return pr.findAll();
    }

    public Optional<Payslip> add(Employee employee, float hour, float wage, float bonus, float malus, Date date) {

        System.out.println(employee);
        System.out.println(date);
        if (employee == null || hour < 0 || wage < 0 || bonus < 0 || malus < 0 || date == null) {
            return Optional.empty();
        }

        return Optional.of(pr.save(new Payslip(employee, hour, wage, bonus, malus, date)));
    }

    public Optional<Payslip> getById(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return pr.findById(id);
    }

    public boolean deleteById(Integer id) {
        if (id == null || id <= 0 || getById(id).isEmpty()) {
            return false;
        }

        pr.deleteById(id);
        return true;
    }

    public List<Payslip> getAllOfEmployee(Integer id) {
        if (id == null || id <= 0) {
            return List.of();
        }

        ArrayList<Payslip> ofEmployee = new ArrayList<>();
        Iterator<Payslip> ite = getAll().iterator();
        while (ite.hasNext()) {
            Payslip p = ite.next();
            if (p.getEmployee().getId().equals(id)) {
                ofEmployee.add(p);
            }
        }

        return Collections.unmodifiableList(ofEmployee);
    }

    public boolean update(Payslip p) {
        if (p == null || getById(p.getId()).isEmpty()) {
            return false;
        }
        pr.save(p);
        return true;
    }

    public byte[] generatePdf(final Integer id) {
        Payslip p = getById(id).orElse(null);
        if (p == null) {
            return null;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Employee employee = p.getEmployee();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            document.add(new Paragraph("FICHE DE PAIE"));
            document.add(new Paragraph("Date: " + sdf.format(p.getDate())));
            document.add(new Paragraph("Employe: " + employee.getFirstName() + " " + employee.getLastName()));
            document.add(new Paragraph(
                    "Salaire de base: " + String.format("%.2f EUR", p.getHour() * p.getWage())));

            if (p.getBonus() > 0) {
                document.add(new Paragraph("Primes: " + String.format("%.2f EUR", p.getBonus())));
            }

            if (p.getMalus() > 0) {
                document.add(new Paragraph("Deductions: " + String.format("%.2f EUR", p.getMalus())));
            }

            document.add(new Paragraph(
                    "TOTAL: " + String.format("%.2f EUR", p.getHour() * p.getWage() + p.getBonus() - p.getMalus())));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
