package com.ecommerce.Ecommerce.Beginner.projects.exception;


import com.ecommerce.Ecommerce.Beginner.projects.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e){
        return  ResponseEntity.status(400).body(new Response(400,e.getMessage(),null));
    }

    @ExceptionHandler(JwtExpired.class)
    public ResponseEntity<?> handleJwtExpiredException(JwtExpired e){
        return ResponseEntity.status(404).body(new Response(404, e.getMessage(),null ));
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException e){
        return ResponseEntity.status(400).body(new Response(400, e.getMessage(),null ));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e){
        return ResponseEntity.status(404).body(new Response(404, e.getMessage(),null ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        return ResponseEntity.status(500).body(new Response(500, "Internal Server Error",null ));
    }

}
