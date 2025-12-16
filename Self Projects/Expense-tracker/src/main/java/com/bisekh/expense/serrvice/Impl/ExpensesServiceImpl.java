package com.bisekh.expense.serrvice.Impl;

import com.bisekh.expense.component.JwtUtil;
import com.bisekh.expense.domain.TransactionType;
import com.bisekh.expense.model.Expenses;
import com.bisekh.expense.repository.ExpenseRepository;
import com.bisekh.expense.serrvice.ExpenseService;
import io.jsonwebtoken.Claims;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
public class ExpensesServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private  final JwtUtil jwtUtil;

    public  ExpensesServiceImpl(ExpenseRepository expenseRepository, JwtUtil jwtUtil){
        this.expenseRepository = expenseRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Expenses postExpenses(Expenses expenses, String accessToken) throws Exception {
        if(expenses == null ){
            throw new Exception("Expenses cannot be empty");
        }
        if(accessToken.isEmpty()){
            throw new Exception("Invalid token");
        }
        Claims token = jwtUtil.decodeToken(accessToken);
        Long userId = token.get("id", Long.class);

        Expenses newExpenses = new Expenses();
        newExpenses.setAmount(expenses.getAmount());
        newExpenses.setUserId(String.valueOf(userId));
        newExpenses.setType(expenses.getType());
        newExpenses.setDate(LocalDate.now());
        newExpenses.setDescription(expenses.getDescription());
        newExpenses.setCategoryType(expenses.getCategoryType());



        return expenseRepository.save(newExpenses);
    }

    @Override
    public List<Expenses> getAllMyExpenses(String accessToken) throws  Exception{
        if(accessToken.isEmpty()){
            throw new Exception("Invalid token");
        }
        Claims token = jwtUtil.decodeToken(accessToken);
        Long userId = token.get("id",Long.class);
        return  expenseRepository.findExpensesByUserId(String.valueOf(userId));
    }

    @Override
    public Long getMonthlyStats(String accessToken, int month, int year, String userId, TransactionType expenseType) throws  Exception {
        if(accessToken.isEmpty()){
            throw new Exception("Invalid token");
        }
        List<Expenses> expensesList = expenseRepository.findExpensesByUserId(userId);
        Long totalAmount = 0L;




        return expensesList.stream()
                .filter(e-> e.getDate() != null
 && e.getType() != null
                    && e.getType().equals(expenseType)
                && e.getDate().getMonthValue() == month
                        && e.getDate().getYear() == year


                )
                .mapToLong(Expenses::getAmount)
                .sum();


    }

    @Override
    public String deleteExpenses(Long expenseId) throws  Exception {
       expenseRepository.deleteById(expenseId);

        return "Delete successful";
    }

    @Override
    public List<Expenses> searchExpenses(String month,int year,String userId) {
        List<Expenses> expensesList = expenseRepository.findExpensesByUserId(userId);
        Month monthEnum = Month.valueOf(month.toUpperCase());
        int monthNumber = monthEnum.getValue();

        return expensesList.stream()
                .filter(e-> e.getDate() !=null &&
                        e.getDate().getMonthValue() == monthNumber
                        && e.getDate().getYear() == year
                        )
                .collect(Collectors.toList());
    }

    @Override
    public List<Expenses> weeklyExpenses(String userId) {
        List<Expenses> getUsersExpenses = expenseRepository.findExpensesByUserId(userId);

        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = today.get(weekFields.weekOfWeekBasedYear());
        int currentYear = today.getYear();

        return getUsersExpenses.stream()
                .filter(e-> e.getDate() !=null
                && e.getDate().getYear() == currentYear
                        && e.getDate().get(weekFields.weekOfWeekBasedYear()) == currentWeek
                ).collect(Collectors.toList());

    }
}
