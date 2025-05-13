package com.example.carrental.dto.request.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private Integer reservationId;

    @NotNull
    private Date dateOfBooking;

    @NotNull
    private Date dateFrom;

    @NotNull
    private LocalDateTime dateTo;

    @NotBlank
    private String returnDepartment;

    @NotNull
    private Double amount;

    @NotNull
    private Integer carId;

    private String userIdSelect;
}
