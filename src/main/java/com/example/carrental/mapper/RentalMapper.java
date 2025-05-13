package com.example.carrental.mapper;

import com.example.carrental.dto.response.rental.RentalResponse;
import com.example.carrental.entity.Rental;
import org.springframework.stereotype.Service;

@Service
public class RentalMapper {

    public static RentalResponse map(Rental rental) {
       return RentalResponse.builder()
                .id(rental.getId())
                .name(rental.getName())
                .internetDomain(rental.getInternetDomain())
                .contactAddress(rental.getContactAddress())
                .owner(rental.getOwner())
                .logoType(rental.getLogoType())
                .build();
    }
}
