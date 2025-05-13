package com.example.carrental.exception;

import com.example.carrental.exception.branch.BranchException;
import com.example.carrental.exception.car.AddCarException;
import com.example.carrental.exception.car.CarNotFoundException;
import com.example.carrental.exception.error.ApiError;
import com.example.carrental.exception.rental.RentalException;
import com.example.carrental.exception.reservation.AddReservationException;
import com.example.carrental.exception.reservation.ReservationNotFoundException;
import com.example.carrental.exception.role.RoleNotFoundException;
import com.example.carrental.exception.user.UserAlreadyTakenException;
import com.example.carrental.exception.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(AddCarException.class)
    public ResponseEntity<Object> handleAddCarException(AddCarException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AddReservationException.class)
    public ResponseEntity<Object> handleReservationException(AddReservationException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Object> handleReservationNotFoundException(ReservationNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RentalException.class)
    public ResponseEntity<Object> handleRentalException(RentalException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BranchException.class)
    public ResponseEntity<Object> handleBranchException(BranchException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Object> handleCarNotFoundException(CarNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UserAlreadyTakenException.class)
    public ResponseEntity<Object> handleUserAlreadyTakenException(UserAlreadyTakenException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }
}

