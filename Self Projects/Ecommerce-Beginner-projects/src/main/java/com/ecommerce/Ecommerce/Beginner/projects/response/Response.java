package com.ecommerce.Ecommerce.Beginner.projects.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Integer status;
    private String message;
    private Object body;
}
