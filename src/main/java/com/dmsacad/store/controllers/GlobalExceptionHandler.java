package com.dmsacad.store.controllers;

import com.dmsacad.store.dtos.ErrorDto;
import com.dmsacad.store.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception){
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach(
                error->{errors.put(error.getField(), error.getDefaultMessage());});
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleUnreadableMessage(){
        return ResponseEntity.badRequest().body(
                Map.of("Error", "Inalid request body")
        );
    }


    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFoundException() {
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Cart not found"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Cart not found"));
    }

    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<ErrorDto> handleCartEmptyException() {
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error", "Cart items is empty"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Cart items is empty"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Product not found in the cart"));
    }

    @ExceptionHandler(ProductOnlyNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException2() {
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error", "Product not found"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Product not found"));
    }


    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCategoryNotFoundException2() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Category not found"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Order not Found"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                //.body(new ErrorDto("You are not allowed to access the resource"));
                .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error when creating a checkout session"));
    }

}
