package com.cps.app.dto;

//Error response
public class ErrorResponse<T> extends ApiResponse<T> {
public ErrorResponse(String message, T data) {
   super(false, message, null);
}
}
