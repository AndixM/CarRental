package com.example.carrental.service;

import com.example.carrental.dto.request.branch.BranchRequest;
import com.example.carrental.dto.request.car.CarRequest;
import com.example.carrental.dto.request.rental.RentalRequest;
import com.example.carrental.dto.request.reservation.ReservationRequest;
import com.example.carrental.dto.request.user.RoleRequest;
import com.example.carrental.dto.request.user.UserRequest;
import com.example.carrental.dto.response.branch.BranchResponse;
import com.example.carrental.dto.response.car.CarResponse;
import com.example.carrental.dto.response.rental.RentalResponse;
import com.example.carrental.dto.response.reservation.ReservationResponse;
import com.example.carrental.dto.response.user.RoleResponse;
import com.example.carrental.dto.response.user.UserResponse;
import com.example.carrental.entity.*;

import java.util.List;

public interface RentalService {

    void populateDB();

    Branch findBranchById(int id);

    Car findCarById(int id);

    Rental findRentalById(int id);

    Reservation findReservationById(int id);

    Role findRoleById(int id);

    User findUserById(int id);

    List<Branch> getAllBranches();

    List<Car> getAllCars();

    List<Rental> getAllRentals();

    List<Reservation> getAllReservations();

    List<Role> getAllRoles();

    List<User> getAllUsers();

    BranchResponse addOrUpdateBranch(BranchRequest branchRequest);

    CarResponse addOrUpdateCar(CarRequest carRequest);

    RentalResponse addRental(RentalRequest rentalRequest);

    ReservationResponse addOrUpdateReservation(ReservationRequest reservationRequest);

    RoleResponse addRole(RoleRequest roleRequest);

    UserResponse addUser(UserRequest userRequest);

    void deleteBranch(Integer id);

    void deleteCarWithReservation(Integer idCar, Integer idReservation);

    void deleteCar(Integer id);

    void deleteUserWithReservation(Integer idUser, Integer idReservation);

    void deleteUserFromReservation(Integer idUser, Integer idReservation);

    void deleteRental(Integer id);

    void deleteReservation(Integer id);

    void deleteRole(Integer id);

    void deleteUser(Integer id);

    BranchResponse updateBranch(BranchRequest branchRequest);

    CarResponse updateCar(CarRequest carRequest);

    RentalResponse updateRental(RentalRequest rentalRequest);

    ReservationResponse updateReservation(ReservationRequest reservationRequest);

    RoleResponse updateRole(RoleRequest roleRequest);

    UserResponse updateUser(UserRequest userRequest);

    BranchResponse addBranchToRental(BranchRequest branchRequest);

    BranchResponse addCarToBranch(Integer carId, Integer branchId);

    ReservationResponse addReservationToUser(Integer userId, Integer reservationId);

    RoleResponse addRoleToUser(Integer roleId, Integer userId);
}
