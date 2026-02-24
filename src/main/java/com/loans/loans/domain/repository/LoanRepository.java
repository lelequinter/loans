package com.loans.loans.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loans.loans.domain.model.Loan;


@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {

}
