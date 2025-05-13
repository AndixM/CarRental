package com.example.carrental.dto.request.rental;

import com.example.carrental.entity.Branch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequest {

    private Integer rentalId;

    @NotBlank
    private String name;

    @NotBlank
    private String internetDomain;

    @NotBlank
    private String contactAddress;

    @NotBlank
    private String owner;

    @NotBlank
    private String logotype;

    @NotNull
    private List<Branch> branchList;
}
