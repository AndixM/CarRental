package com.example.carrental.dto.response.reservation;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private Integer id;
    private Date date;
    private Integer userId;
    private Car car;
    private Date dateFrom;
    private LocalDateTime dateTo;
    private String returnDepartment;
    private Double amount;

}
