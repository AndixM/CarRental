package com.example.carrental.dto.response.branch;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Rental;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponse {

    private int id;
    private String name;
    private String address;
    private List<Car> carList;
    private Rental rental;
}
