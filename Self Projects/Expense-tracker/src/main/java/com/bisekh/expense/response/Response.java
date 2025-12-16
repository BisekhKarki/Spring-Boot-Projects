package com.bisekh.expense.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
private int status;
private String message;
private Object body;
}
