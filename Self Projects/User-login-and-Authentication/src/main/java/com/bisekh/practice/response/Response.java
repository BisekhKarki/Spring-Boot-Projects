package com.bisekh.practice.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private int status;
    private Object message;

}
