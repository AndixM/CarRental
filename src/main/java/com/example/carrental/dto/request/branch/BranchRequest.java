package com.example.carrental.dto.request.branch;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Rental;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchRequest {

    private Integer branchId;

    private String name;

    private String address;

    private List<Integer> carList;

    private Integer rentalId;
}
