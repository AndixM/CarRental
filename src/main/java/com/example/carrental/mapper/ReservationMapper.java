package com.example.carrental.mapper;

import com.example.carrental.dto.response.reservation.ReservationResponse;
import com.example.carrental.entity.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {
    public static ReservationResponse map(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getReservationId())
                .date(reservation.getDateOfBooking())
                .dateFrom(reservation.getDateFrom())
                .dateTo(reservation.getDateTo())
                .returnDepartment(reservation.getReturnDepartment())
                .amount(reservation.getAmount())
                .car(reservation.getCar())
                .userId(reservation.getUser().getUserId())
                .build();
    }
}
