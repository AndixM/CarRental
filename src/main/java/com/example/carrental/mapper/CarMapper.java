package com.example.carrental.mapper;

import com.example.carrental.dto.response.car.CarResponse;
import com.example.carrental.entity.Car;
import org.springframework.stereotype.Service;

@Service
public class CarMapper {

    public static CarResponse map(Car car) {
        return CarResponse.builder()
                .id(car.getCarId())
                .brand(car.getBrand())
                .model(car.getModel())
                .bodyType(car.getBodyType())
                .year(car.getYear())
                .color(car.getColor())
                .mileage(car.getMileage())
                .status(car.getStatus())
                .amount(car.getAmount())
                .branch(car.getBranch())
                .build();
    }


}
