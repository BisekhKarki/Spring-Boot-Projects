package com.bisekh.expense.controller;


import com.bisekh.expense.component.JwtUtil;
import com.bisekh.expense.domain.TransactionType;
import com.bisekh.expense.model.Expenses;
import com.bisekh.expense.model.User;
import com.bisekh.expense.repository.ExpenseRepository;
import com.bisekh.expense.repository.UserRepository;
import com.bisekh.expense.response.Response;
import com.bisekh.expense.serrvice.Impl.ExpensesServiceImpl;
import com.bisekh.expense.serrvice.UserService;
import io.jsonwebtoken.Claims;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final ExpensesServiceImpl expensesService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public ExpenseController(ExpenseRepository expenseRepository,
                             ExpensesServiceImpl expensesService,
                             JwtUtil jwtUtil,
                             UserRepository userRepository
    ){
        this.expenseRepository = expenseRepository;
        this.expensesService = expensesService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

//    Expenses postExpenses(Expenses expenses, String accessToken) throws  Exception;
//    List<Expenses> getAllMyExpenses(String accessToken) throws  Exception;
//    Long getMonthlyStats(String accessToken) throws  Exception;
//    String deleteExpenses( Long expenseId) throws  Exception;

    @PostMapping("/create")
    public ResponseEntity<?> postExpenses(
            @RequestBody Expenses expenses,
            @RequestHeader("Authorization") String accessToken
            ){
        try{
            String token = accessToken.replace("Bearer ","");
            Expenses newExpenses = expensesService.postExpenses(expenses,token);
            if(newExpenses == null){
                return  ResponseEntity.status(400).body(new Response(
                        400,
                        "Failed to save expenses",
                        null)
                );
            }
            return  ResponseEntity.status(201).body(new Response(
                    201,
                    "Expenses has been created successfully",
                    newExpenses)
            );
        } catch (Exception e) {
            String error = e.getMessage();
            return  ResponseEntity.status(500).body(new Response(500,error,null));
        }

    }


    @GetMapping("/expenses")
    public ResponseEntity<?> getAllExpenses(
            @RequestHeader("Authorization") String accessToken
    ){
        try{
            String token = accessToken.replace("Bearer ", "");
            List<Expenses> getMyExpenses = expensesService.getAllMyExpenses(token);
            if(getMyExpenses.isEmpty()){
                return  ResponseEntity.status(400).body(new Response(
                        400,
                        "No expenses found",
                        null)
                );
            }
            return  ResponseEntity.status(200).body(new Response(
                    200,
                    "All Expenses",
                    getMyExpenses)
            );
        }
        catch (Exception e) {
            String error = e.getMessage();
            return  ResponseEntity.status(500).body(new Response(500,error,null));
        }
    }



    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<?> deleteMyExpense(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long expenseId
    ){
        try{
            String token = accessToken.replace("Bearer ", "");
            Claims decodedToken = jwtUtil.decodeToken(token);
            Long userId = Long.valueOf(decodedToken.getId());
            Expenses getExpense = expenseRepository.findById(expenseId).orElse(null);
            if(getExpense == null){
                return  ResponseEntity.status(400).body(new Response(
                        400,
                        "no expense found",
                        null)
                );
            }
            Long expenseUserId = Long.valueOf(getExpense.getUserId());

            if(!expenseUserId.equals(userId)){
                return  ResponseEntity.status(404).body(new Response(
                        404,
                        "Unauthorized",
                        null)
                );
            }
            String deleteExpenses = expensesService.deleteExpenses(expenseId);
            if(deleteExpenses.isEmpty()){
                return  ResponseEntity.status(400).body(new Response(
                        400,
                        "Failed to delete expense",
                        null)
                );
            }
            return  ResponseEntity.status(200).body(new Response(
                    200,
                    deleteExpenses,
                    null)
            );
        }
        catch (Exception e) {
            String error = e.getMessage();
            return  ResponseEntity.status(500).body(new Response(500,error,null));
        }
    }


    @GetMapping("/expenses/monthly")
    public ResponseEntity<?> getMonthlyTotalExpenses(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam String month,
            @RequestParam int year,
            @RequestParam String type
    ){
        try{
            String token = accessToken.replace("Bearer ", "");
            Claims decodedToken = jwtUtil.decodeToken(token);
            Long userId = decodedToken.get("id",Long.class);
            String email = decodedToken.getSubject();
            Month monthEnum = Month.valueOf(month.toUpperCase());
            int monthNumber = monthEnum.getValue();
            TransactionType transactionType = TransactionType.valueOf(type);

            User userDetails = userRepository.findByEmail(email);
            if(!userDetails.getEmail().equals(email)){
                return  ResponseEntity.status(404).body(new Response(
                        404,
                        "Unauthorized",
                        null)
                );
            }
            Long getMyExpenses = expensesService.getMonthlyStats(token,monthNumber,year,String.valueOf(userId),transactionType);

            return  ResponseEntity.status(200).body(new Response(
                    200,
                    "Total Monthly Expenses",
                    getMyExpenses)
            );
        }
        catch (Exception e) {
            String error = e.getMessage();
            return  ResponseEntity.status(500).body(new Response(500,error,null));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchExpense(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam String month,
            @RequestParam int year
    ){
        try{
            String token = accessToken.replace("Bearer ", "");
            Claims decodedToken = jwtUtil.decodeToken(token);
            Long userId = decodedToken.get("id",Long.class);
            String email = decodedToken.getSubject();
            User userDetails = userRepository.findByEmail(email);

            if(!userDetails.getEmail().equals(email)){
                return  ResponseEntity.status(404).body(new Response(
                        404,
                        "Unauthorized",
                        null)
                );
            }
            List<Expenses> getMyExpenses = expensesService.searchExpenses(month,year,String.valueOf(userId));
            if(getMyExpenses.isEmpty()){
                return  ResponseEntity.status(400).body(new Response(
                        400,
                        "No expenses found",
                        null)
                );
            }
            return  ResponseEntity.status(200).body(new Response(
                    200,
                    "All Expenses",
                    getMyExpenses)
            );
        }
        catch (Exception e) {
            String error = e.getMessage();
            return  ResponseEntity.status(500).body(new Response(500,error,null));
        }
    }


    @GetMapping("/expenses/weekly")
    public ResponseEntity<?> getWeeklyTotalExpenses(
            @RequestHeader("Authorization") String accessToken

    ){
        try{
            String token = accessToken.replace("Bearer ", "");
            Claims decodedToken = jwtUtil.decodeToken(token);
            Long userId = decodedToken.get("id",Long.class);
            String email = decodedToken.getSubject();


            User userDetails = userRepository.findByEmail(email);
            if(!userDetails.getEmail().equals(email)){
                return  ResponseEntity.status(404).body(new Response(
                        404,
                        "Unauthorized",
                        null)
                );
            }
            List<Expenses> getMyExpenses = expensesService.weeklyExpenses(String.valueOf(userId));

            return  ResponseEntity.status(200).body(new Response(
                    200,
                    "Weekly Expenses",
                    getMyExpenses)
            );
        }
        catch (Exception e) {
            String error = e.getMessage();
            return  ResponseEntity.status(500).body(new Response(500,error,null));
        }
    }


}
