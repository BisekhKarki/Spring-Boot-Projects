package com.bisekh.expense.serrvice;

import com.bisekh.expense.domain.TransactionType;
import com.bisekh.expense.model.Expenses;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ExpenseService {

    Expenses postExpenses(Expenses expenses, String accessToken) throws  Exception;
    List<Expenses> getAllMyExpenses(String accessToken) throws  Exception;
    Long getMonthlyStats(String accessToken, int month, int year,String userId, TransactionType expenseType) throws  Exception;
    String deleteExpenses( Long expenseId) throws  Exception;

    List<Expenses> searchExpenses(String month,int year,String userId);
    List<Expenses> weeklyExpenses(String userId);


}
