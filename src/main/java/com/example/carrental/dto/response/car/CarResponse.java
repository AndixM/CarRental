package com.example.carrental.dto.response.car;

import com.example.carrental.entity.Branch;
import com.example.carrental.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarResponse {

    private Integer id;
    private String brand;
    private String model;
    private String bodyType;
    private Integer year;
    private String color;
    private Double mileage;
    private Status status;
    private Double amount;
    private Branch branch;
}
