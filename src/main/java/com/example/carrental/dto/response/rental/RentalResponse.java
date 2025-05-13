package com.example.carrental.dto.response.rental;

import com.example.carrental.entity.Branch;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalResponse {

    private Integer id;
    private String name;
    private String internetDomain;
    private String contactAddress;
    private String owner;
    private String logoType;

}
