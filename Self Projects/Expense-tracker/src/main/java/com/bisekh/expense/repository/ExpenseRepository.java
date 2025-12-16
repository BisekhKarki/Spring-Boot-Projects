package com.bisekh.expense.repository;

import com.bisekh.expense.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expenses, Long> {
    List<Expenses> findExpensesByUserId(String userId);

    List<Expenses> findByUserIdAndDate(String userId, LocalDate date);
}
