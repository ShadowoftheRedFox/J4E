package fr.cyu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cyu.controller.dto.NewPayslipDTO;
import fr.cyu.data.employee.EmployeeService;
import fr.cyu.data.payslip.Payslip;
import fr.cyu.data.payslip.PayslipService;
import fr.cyu.utils.JSONUtil;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/payslip")
public class PayslipController {
    @Autowired
    private PayslipService ps;

    @Autowired
    private EmployeeService es;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAll() {
        return JSONUtil.stringify(ps.getAll());
    }

    @GetMapping(value = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllOfEmployee(@PathVariable("id") Integer id) {
        if (id <= 0) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        if (es.getById(id).isEmpty()) {
            return JSONUtil.NOT_FOUND_ERROR;
        }

        return ResponseEntity.ok(JSONUtil.stringify(ps.getAllOfEmployee(id)));
    }

    @PostMapping(value = "")
    public ResponseEntity<String> newPayslip(@Valid @RequestBody NewPayslipDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        boolean res = ps.add(es.getById(dto.getEmployee()).orElse(null), dto.getHour(), dto.getWage(), dto.getBonus(),
                dto.getMalus(), dto.getDate()).isPresent();

        return res ? JSONUtil.OK : JSONUtil.NOT_YET_IMPLEMENTED;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@PathVariable("id") Integer id) {
        if (id <= 0) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        Optional<Payslip> e = ps.getById(id);
        if (e.isEmpty()) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        return ResponseEntity.ok(JSONUtil.stringify(e.get()));
    }

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public Object getPdf(@PathVariable("id") Integer id) {
        if (id <= 0) {
            return JSONUtil.NOT_FOUND_ERROR;
        }
        Payslip p = ps.getById(id).orElse(null);
        if (p == null) {
            return JSONUtil.NOT_FOUND_ERROR;
        }

        return ps.generatePdf(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> editPayslip(@PathVariable("id") Integer id, @Valid @RequestBody NewPayslipDTO dto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }

        Payslip p = ps.getById(id).orElse(null);
        if (p == null) {
            return JSONUtil.NOT_FOUND_ERROR;
        }

        p.setBonus(dto.getBonus());
        p.setDate(dto.getDate());
        p.setHour(dto.getHour());
        p.setMalus(dto.getMalus());
        p.setWage(dto.getWage());

        return ps.update(p) ? JSONUtil.OK : JSONUtil.SERVER_ERROR;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return JSONUtil.BAD_REQUEST_ERROR;
        }
        boolean res = ps.deleteById(id);

        return res ? JSONUtil.OK : JSONUtil.NOT_FOUND_ERROR;
    }
}
