package com.cps.app.dto;

//Success response
public class SuccessResponse<T> extends ApiResponse<T> {
public SuccessResponse(String message, T data) {
   super(true, message, data);
}
}
