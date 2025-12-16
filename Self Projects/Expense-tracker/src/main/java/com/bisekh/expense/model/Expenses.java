package com.bisekh.expense.model;

import com.bisekh.expense.domain.CategoryType;
import com.bisekh.expense.domain.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @Column(nullable = false)
    private Long amount;
    private String description;
    private LocalDate date;


//    public  Expenses(Long id,
//                     TransactionType type,
//                     CategoryType categoryType,
//                     Long amount,
//                     String description,
//                     LocalDate date
//                     ){
//        this.amount = amount;
//        this.date = date;
//        this.type = type;
//        this.categoryType = categoryType;
//        this.description = description;
//        this.id = id;
//    }

}
