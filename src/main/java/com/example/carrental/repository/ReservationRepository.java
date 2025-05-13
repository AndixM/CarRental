package com.example.carrental.repository;

import com.example.carrental.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "delete from Reservation reservation where reservation.car.id=:carId", nativeQuery = true)
    Reservation deleteReservationByCarId(Integer carId);

    @Query("SELECT r FROM Reservation r WHERE r.car.id = :carId")
    List<Reservation> findByCarId(Integer carId);
}
