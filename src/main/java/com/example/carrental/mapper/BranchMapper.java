package com.example.carrental.mapper;

import com.example.carrental.dto.response.branch.BranchResponse;
import com.example.carrental.entity.Branch;
import com.example.carrental.entity.Car;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchMapper {

    public static BranchResponse map(Branch branch, List<Car> carList) {
        return BranchResponse.builder()
                .id(branch.getBranchId())
                .name(branch.getName())
                .address(branch.getAddress())
                .carList(carList.stream().filter(car -> car.getBranch() != null && car.getBranch().getBranchId() == branch.getBranchId()).toList())
                .rental(branch.getRental())
                .build();
    }


    public static BranchResponse map(Branch branch) {
        return BranchResponse.builder()
                .id(branch.getBranchId())
                .name(branch.getName())
                .address(branch.getAddress())
                .build();
    }
}
