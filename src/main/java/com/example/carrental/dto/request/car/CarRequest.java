package com.example.carrental.dto.request.car;

import com.example.carrental.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    private Integer carId;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String bodyType;

    @NotNull
    private Integer year;

    @NotBlank
    private String color;

    @NotBlank
    private Double mileage;

    @NotNull
    private Status status;

    @NotNull
    private Double amount;

    @NotNull
    private Integer branchId;
}
