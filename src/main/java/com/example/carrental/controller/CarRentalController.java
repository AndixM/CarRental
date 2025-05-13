package com.example.carrental.controller;

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
import com.example.carrental.enums.Status;
import com.example.carrental.mapper.*;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.service.RentalServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CarRentalController {

    private final RentalServiceImpl serviceImpl;
    private final CarRepository carRepository;

    CarRentalController(RentalServiceImpl serviceImpl, CarRepository carRepository) {
        this.serviceImpl = serviceImpl;
        this.carRepository = carRepository;
    }

    @PutMapping("/api/populateDB")
    public void populateDB() {
        serviceImpl.populateDB();
    }

    @GetMapping("/api/branch/{id}")
    public BranchResponse getBranchById(@PathVariable Integer id) {
        Branch branch = serviceImpl.findBranchById(id);
        List<Car> carList = serviceImpl.getAllCars();

        return BranchMapper.map(branch, carList);
    }

    @GetMapping(path = "/api/car/{id}")
    public CarResponse getCarById(@PathVariable("id") Integer id) {
        Car car = serviceImpl.findCarById(id);
        return CarMapper.map(car);
    }

    @GetMapping(path = "/api/rental/{id}")
    public RentalResponse getRentalById(@PathVariable Integer id) {
        Rental rental = serviceImpl.findRentalById(id);
        return RentalMapper.map(rental);
    }

    @GetMapping(path = "/api/reservation/{id}")
    public ReservationResponse getReservationById(@PathVariable Integer id) {
        Reservation reservation = serviceImpl.findReservationById(id);
        return ReservationMapper.map(reservation);
    }

    @GetMapping(path = "/api/roles/{id}")
    public RoleResponse getRolesById(@PathVariable Integer id) {
        Role role = serviceImpl.findRoleById(id);
        return RoleMapper.map(role);
    }

    @GetMapping("/api/user/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        User user = serviceImpl.findUserById(id);
        return UserMapper.map(user);
    }

    @GetMapping(path = "/api/branch")
    public List<BranchResponse> getAllBranches() {
        List<Branch> branchList = serviceImpl.getAllBranches();
        List<BranchResponse> branchResponse = new ArrayList<>();
        List<Car> carList = serviceImpl.getAllCars();
        branchList.forEach(branch -> branchResponse.add(BranchMapper.map(branch, carList)));
        return branchResponse;
    }

    @GetMapping(path = "/api/car")
    public List<CarResponse> getAllCars() {
        List<Car> carList = serviceImpl.getAllCars();
        List<CarResponse> carResponse = new ArrayList<>();
        carList.forEach(car -> carResponse.add(CarMapper.map(car)));
        return carResponse;
    }

    @GetMapping(path = "/api/rentals")
    public List<RentalResponse> getAllRentals() {
        List<Rental> rentalList = serviceImpl.getAllRentals();
        List<RentalResponse> rentalResponse = new ArrayList<>();
        rentalList.forEach(rent -> rentalResponse.add(RentalMapper.map(rent)));
        return rentalResponse;
    }

    @GetMapping("/api/reservations")
    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservationList = serviceImpl.getAllReservations();
        List<ReservationResponse> reservationResponse = new ArrayList<>();
        reservationList.forEach(reservation -> reservationResponse.add(ReservationMapper.map(reservation)));
        return reservationResponse;
    }

    @GetMapping("/api/roles")
    public List<RoleResponse> getAllRoles() {
        List<Role> roleList = serviceImpl.getAllRoles();
        List<RoleResponse> roleResponse = new ArrayList<>();
        roleList.forEach(role -> roleResponse.add(RoleMapper.map(role)));
        return roleResponse;
    }

    @GetMapping("/api/users")
    public List<UserResponse> getAllUsers() {
        List<User> userList = serviceImpl.getAllUsers();
        List<UserResponse> userResponse = new ArrayList<>();
        userList.forEach(user -> userResponse.add(UserMapper.map(user)));
        return userResponse;
    }

    @GetMapping("/api/statuses")
    public List<Status> getAllStatuses() {
        return Arrays.asList(Status.values());
    }

    @PutMapping("/api/addBranch")
    public ResponseEntity<BranchResponse> addBranch(@RequestBody BranchRequest branchRequest) {
        BranchResponse branchResponse = serviceImpl.addOrUpdateBranch(branchRequest);
        return ResponseEntity.ok(branchResponse);
    }

    @PutMapping("/api/addCar")
    public ResponseEntity<CarResponse> addOrUpdateCar(@RequestBody CarRequest carRequest) {
        CarResponse carResponse = serviceImpl.addOrUpdateCar(carRequest);
        return ResponseEntity.ok(carResponse);
    }

    @PutMapping("api/addRental")
    public ResponseEntity<RentalResponse> addRental(@RequestBody RentalRequest rentalRequest) {
        RentalResponse rentalResponse = serviceImpl.addRental(rentalRequest);
        return ResponseEntity.ok(rentalResponse);
    }

    @PutMapping("/api/addReservation")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = serviceImpl.addOrUpdateReservation(reservationRequest);
        return ResponseEntity.ok(reservationResponse);
    }

    @PutMapping("/api/addRole")
    public ResponseEntity<RoleResponse> addRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = serviceImpl.addRole(roleRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @PutMapping("/api/addUser")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = serviceImpl.addUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/api/deleteBranch/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Integer id) {
        serviceImpl.deleteBranch(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/api/deleteCarWithReservation/{idCar}/{idReservation}")
    public void deleteCarWithReservation(@PathVariable Integer idCar, @PathVariable Integer idReservation) {
        serviceImpl.deleteCarWithReservation(idCar, idReservation);
    }

    @DeleteMapping("/api/deleteCar/{id}")
    public void deleteCar(@PathVariable Integer id){
        serviceImpl.deleteCar(id);
    }


    @DeleteMapping("api/deleteRental/{id}")
    public void deleteRental(@PathVariable Integer id) {
        serviceImpl.deleteRental(id);
    }

    @DeleteMapping("api/deleteReservation/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        serviceImpl.deleteReservation(id);
    }

    @DeleteMapping("api/deleteRole/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id) {
        serviceImpl.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body("Role deleted");
    }

    @DeleteMapping("api/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        serviceImpl.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

    @DeleteMapping("api/deleteUserWithReservation/{idUser}/{idReservation}")
    public void deleteUserWithReservation(@PathVariable Integer idUser, @PathVariable Integer idReservation) {
        serviceImpl.deleteUserWithReservation(idUser, idReservation);
    }

    @DeleteMapping("api/deleteUserFromReservation/{idUser}/{idReservation}")
    public void deleteUserFromReservation(@PathVariable Integer idUser, @PathVariable Integer idReservation) {
        serviceImpl.deleteUserFromReservation(idUser, idReservation);
    }

    @PostMapping("/api/updateBranch")
    public ResponseEntity<BranchResponse> updateBranch(@RequestBody BranchRequest branchRequest) {
        BranchResponse branchResponse = serviceImpl.updateBranch(branchRequest);
        return ResponseEntity.ok(branchResponse);
    }

    @PostMapping("/api/updateCar")
    public ResponseEntity<CarResponse> updateCar(@RequestBody CarRequest carRequest) {
        CarResponse carResponse = serviceImpl.updateCar(carRequest);
        return ResponseEntity.ok(carResponse);
    }

    @PostMapping("api/updateRental")
    public ResponseEntity<RentalResponse> updateRental(@RequestBody RentalRequest rentalRequest) {
        RentalResponse rentalResponse = serviceImpl.updateRental(rentalRequest);
        return ResponseEntity.ok(rentalResponse);
    }

    @PostMapping("/api/updateReservation")
    public ResponseEntity<ReservationResponse> updateReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = serviceImpl.updateReservation(reservationRequest);
        return ResponseEntity.ok(reservationResponse);
    }

    @PostMapping("/api/updateRole")
    public ResponseEntity<RoleResponse> updateRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = serviceImpl.updateRole(roleRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @PostMapping("/api/updateUser")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = serviceImpl.updateUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/api/addBranchToRental")
    public ResponseEntity<BranchResponse> addBranchToRental(@RequestBody BranchRequest branchRequest) {
        BranchResponse branchResponse = serviceImpl.addBranchToRental(branchRequest);
        return ResponseEntity.ok(branchResponse);
    }

    @PostMapping("/api/addCarToBranch/{carId}/{branchId}")
    public ResponseEntity<BranchResponse> addCarToBranch(@PathVariable int carId, @PathVariable int branchId) {
        BranchResponse branchResponse = serviceImpl.addCarToBranch(carId, branchId);
        return ResponseEntity.ok(branchResponse);
    }

    @PostMapping("/api/addReservationToUser/{userId}/{reservationId}")
    public ResponseEntity<ReservationResponse> addReservationToUser(@PathVariable int userId, @PathVariable int reservationId) {
        ReservationResponse reservationResponse = serviceImpl.addReservationToUser(userId, reservationId);
        return ResponseEntity.ok(reservationResponse);
    }

    @PostMapping("/api/addRoleToUser/{roleId}/{userId}")
    public ResponseEntity<RoleResponse> addRoleToUser(@PathVariable int roleId, @PathVariable int userId) {
        RoleResponse roleResponse = serviceImpl.addRoleToUser(roleId, userId);
        return ResponseEntity.ok(roleResponse);
    }
}
