package fr.cyu.jee.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.beans.Department;
import fr.cyu.jee.beans.Payslip;

public class PayslipDAO implements DAO<Payslip> {
    @Override
    public boolean delete(int id) {
        return HibernateUtil.remove(id, Payslip.class);
    }

    @Override
    public Collection<Payslip> getAll() {
        Optional<List<Payslip>> res = HibernateUtil.list("User", Payslip.class);
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
        return HibernateUtil.save(payslip);
    }

    @Override
    public boolean edit(final Payslip p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<Payslip> find(Map<String, Object> filter) {
        // TODO Auto-generated method stub
        return null;
    }

    public void generate(Payslip payslip) {
    }
}