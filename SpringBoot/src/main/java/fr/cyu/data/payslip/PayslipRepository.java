package fr.cyu.data.payslip;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayslipRepository extends JpaRepository<Payslip, Integer> {
}
