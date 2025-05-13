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
import com.example.carrental.enums.Status;
import com.example.carrental.exception.car.CarNotFoundException;
import com.example.carrental.exception.reservation.AddReservationException;
import com.example.carrental.exception.role.RoleNotFoundException;
import com.example.carrental.exception.user.UserNotFoundException;
import com.example.carrental.mapper.*;
import com.example.carrental.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Branch findBranchById(int id) {
        return branchRepository.findById(id).orElse(null);
    }

    @Override
    public Car findCarById(int id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public Rental findRentalById(int id) {
        return rentalRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation findReservationById(int id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public BranchResponse addOrUpdateBranch(BranchRequest branchRequest) {
        Rental rental = rentalRepository.findById(branchRequest.getRentalId()).orElse(null);
        Branch branch = Branch.builder()
                .name(branchRequest.getName())
                .address(branchRequest.getAddress())
                .rental(rental)
                .build();
        if (branchRequest.getBranchId() != null) {
            branch.setBranchId(branchRequest.getBranchId());
        }
        Branch savedBranch = branchRepository.save(branch);


        List<Car> cars = carRepository.findAll();
        List<Car> carListToBeDeleted = cars.stream()
                .filter(car ->  car.getBranch() != null && car.getBranch().getBranchId() == savedBranch.getBranchId())
                .toList();
        if (!carListToBeDeleted.isEmpty())
            carListToBeDeleted.forEach(car -> {
                car.setBranch(null);
                carRepository.save(car);
            });


        List<Car> carListToBeUpdated = cars.stream()
                .filter(car ->  car.getBranch() != null &&  branchRequest.getCarList().stream()
                        .anyMatch(requestCarId ->  requestCarId.equals(car.getCarId())))
                .toList();

        if (!carListToBeUpdated.isEmpty())
            carListToBeUpdated.forEach(car -> {
                        car.setBranch(savedBranch);
                        carRepository.save(car);
                    }
            );


        return BranchMapper.map(savedBranch);
    }

    @Override
    public CarResponse addOrUpdateCar(CarRequest carRequest) {
        Branch branch = findBranchById(carRequest.getBranchId());
        Car car = Car.builder()
                .brand(carRequest.getBrand())
                .model(carRequest.getModel())
                .bodyType(carRequest.getBodyType())
                .year(carRequest.getYear())
                .color(carRequest.getColor())
                .mileage(carRequest.getMileage())
                .status(carRequest.getStatus())
                .amount(carRequest.getAmount())
                .branch(branch)
                .build();
        if (carRequest.getCarId() != null) {
            car.setCarId(carRequest.getCarId());
        }

        Car savedCar = carRepository.save(car);
        return CarMapper.map(savedCar);
    }

    @Override
    public RentalResponse addRental(RentalRequest rentalRequest) {
        Rental savedRental = rentalRepository.save(Rental.builder().name(rentalRequest.getName()).internetDomain(rentalRequest.getInternetDomain()).contactAddress(rentalRequest.getContactAddress()).owner(rentalRequest.getOwner()).logoType(rentalRequest.getLogotype()).build());
        return RentalMapper.map(savedRental);
    }

    @Override
    public ReservationResponse addOrUpdateReservation(ReservationRequest reservationRequest) {
        Car car = findCarById(reservationRequest.getCarId());
        Branch branch = branchRepository.findByBranchName(reservationRequest.getReturnDepartment()).orElse(null);
        User user = userRepository.findById(Integer.valueOf(reservationRequest.getUserIdSelect())).orElse(null);
        Reservation reservation = Reservation.builder()
                .dateOfBooking(reservationRequest.getDateOfBooking())
                .dateFrom(reservationRequest.getDateFrom())
                .dateTo(reservationRequest.getDateTo())
                .amount(reservationRequest.getAmount())
                .car(car)
                .user(user)
                .build();

        if (branch != null) {
            reservation.setReturnDepartment(branch.getName());
        }

        if (reservationRequest.getReservationId() != null) {
            reservation.setReservationId(reservationRequest.getReservationId());
        }

        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.map(savedReservation);
    }

    @Override
    public RoleResponse addRole(RoleRequest roleRequest) {
        Role savedRole = roleRepository.save(Role.builder()
                .name(roleRequest.getName()).build());
        return RoleMapper.map(savedRole);

    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        return getUserResponse(userRequest);
    }

    @Override
    public void deleteBranch(Integer id) {
        branchRepository.deleteById(id);
    }

    @Transactional
    public void deleteCarWithReservation(Integer idCar, Integer idReservation) {

        Optional<Reservation> reservation = reservationRepository.findById(idReservation);


        Optional<Car> carToBeDeleted = carRepository.findById(idCar);
        if (carToBeDeleted.isPresent() && reservation.isPresent()) {
            Reservation reservationFomCar = reservation.get();
            reservationFomCar.setCar(null);
            reservationRepository.save(reservationFomCar);

            carRepository.deleteById(idCar);
        } else {
            throw new CarNotFoundException("Car not found");
        }


    }

    @Transactional
    public void deleteCar(Integer id) {
        List<Reservation> reservation = reservationRepository.findByCarId(id);
        if (!reservation.isEmpty()) {
            reservationRepository.deleteReservationByCarId(id);
        }

        Car car = carRepository.findById(id).orElse(null);
        if (car != null) {
            carRepository.deleteById(id);
        }
    }


    @Override
    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public void deleteRental(Integer id) {
        rentalRepository.deleteById(id);
    }

    @Override
    public void deleteRole(Integer id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new RoleNotFoundException("Role not found");
        }


    }

    @Transactional
    public void deleteUserWithReservation(Integer idUser, Integer idReservation) {
        Optional<Reservation> reservation = reservationRepository.findById(idReservation);
        Optional<User> userToBeDeleted = userRepository.findById(idUser);
        if (userToBeDeleted.isPresent() && reservation.isPresent()) {
            Reservation reservationFromUser = reservation.get();
            reservationFromUser.setUser(null);
            reservationRepository.save(reservationFromUser);

            userRepository.deleteById(idUser);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public void deleteUserFromReservation(Integer idUser, Integer idReservation) {
        Optional<Reservation> reservation = reservationRepository.findById(idReservation);
        Optional<User> userToBeDeleted = userRepository.findById(idUser);
        if (userToBeDeleted.isPresent() && reservation.isPresent()) {
            Reservation reservationFromUser = reservation.get();
            reservationFromUser.setUser(null);
            reservationRepository.save(reservationFromUser);


        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public void deleteUser(Integer id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found");
        }

    }

    @Override
    public BranchResponse updateBranch(BranchRequest branchRequest) {
        Branch branch = findBranchById(branchRequest.getBranchId());
        if (branch == null) {
            return null;
        }
        Branch updatedBranch = branchRepository.save(Branch.builder().name(branchRequest.getName()).address(branchRequest.getAddress()).build());
        return BranchMapper.map(updatedBranch);
    }

    @Override
    public CarResponse updateCar(CarRequest carRequest) {
        Car car = findCarById(carRequest.getCarId());
        if (car == null) {
            return null;
        }
        Car updatedCar = carRepository.save(Car.builder().brand(carRequest.getBrand()).model(carRequest.getModel()).bodyType(carRequest.getBodyType()).year(carRequest.getYear()).color(carRequest.getColor()).mileage(carRequest.getMileage()).status(carRequest.getStatus()).amount(carRequest.getAmount()).build());
        return CarMapper.map(updatedCar);
    }

    @Override
    public ReservationResponse updateReservation(ReservationRequest reservationRequest) {
        Reservation reservation = findReservationById(reservationRequest.getReservationId());
        if (reservation == null) {
            return null;
        }
        Reservation updatedReservation = reservationRepository.save(Reservation.builder()
                .dateOfBooking(reservationRequest.getDateOfBooking()).dateFrom(reservationRequest.getDateFrom()).dateTo(reservationRequest.getDateTo()).amount(reservationRequest.getAmount()).build());
        return ReservationMapper.map(updatedReservation);
    }

    @Override
    public RentalResponse updateRental(RentalRequest rentalRequest) {
        Rental rental = findRentalById(rentalRequest.getRentalId());
        if (rental == null) {
            return null;
        }
        Rental updatedRental = rentalRepository.save(Rental.builder().name(rentalRequest.getName()).internetDomain(rentalRequest.getInternetDomain()).contactAddress(rentalRequest.getContactAddress()).owner(rentalRequest.getOwner()).logoType(rentalRequest.getLogotype()).build());
        return RentalMapper.map(updatedRental);
    }

    @Override
    public RoleResponse updateRole(RoleRequest roleRequest) {
        Role role = findRoleById(roleRequest.getRoleId());
        if (role == null) {
            return null;
        }
        Role updatedRole = roleRepository.save(Role.builder()
                .name(roleRequest.getName()).build());
        return RoleMapper.map(updatedRole);

    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        User user = findUserById(userRequest.getUserId());
        if (user == null) {
            return null;
        }
        return getUserResponse(userRequest);
    }

    private UserResponse getUserResponse(UserRequest userRequest) {
        Optional<Role> role = roleRepository.findRoleByName(userRequest.getRole());
        User updatedUser = userRepository.save(User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .address(userRequest.getAddress())
                .age(userRequest.getAge())
                .phoneNumber(userRequest.getPhoneNumber())
                .roles(Set.of(role.orElse(new Role()))).build());
        return UserMapper.map(updatedUser);
    }

    @Override
    public BranchResponse addBranchToRental(BranchRequest branchRequest) {
        Branch branch = findBranchById(branchRequest.getBranchId());
        Rental rental = findRentalById(branchRequest.getRentalId());
        branch.setRental(rental);
        branchRepository.save(branch);
        return BranchMapper.map(branch);
    }

    @Override
    public BranchResponse addCarToBranch(Integer carId, Integer branchId) {
        Car currentCar = carRepository.findById(carId).orElse(null);
        Branch currentBranch = branchRepository.findById(branchId).orElse(null);
        if (currentCar != null && currentBranch != null) {
            currentCar.setBranch(currentBranch);
            carRepository.save(currentCar);
        } else {
            throw new RuntimeException("Car can not be added to the current branch");
        }
        return BranchMapper.map(currentBranch);
    }

    @Override
    public ReservationResponse addReservationToUser(Integer userId, Integer reservationId) {
        User currentUser = userRepository.findById(userId).orElse(null);
        Reservation currentReservation = reservationRepository.findById(reservationId).orElse(null);
        if (currentUser != null && currentReservation != null) {
            currentReservation.setUser(currentUser);
            reservationRepository.save(currentReservation);
        } else {
            throw new AddReservationException("Reservation not found");
        }
        return ReservationMapper.map(currentReservation);
    }

    @Override
    public RoleResponse addRoleToUser(Integer roleId, Integer userId) {
        Role currentRole = roleRepository.findById(roleId).orElse(null);
        User currentUser = userRepository.findById(userId).orElse(null);
        if (currentRole != null && currentUser != null) {
            Set<Role> roles = currentUser.getRoles();
            roles.add(currentRole);
            currentUser.setRoles(roles);
            userRepository.save(currentUser);
        } else {
            throw new RoleNotFoundException("Role not found");
        }
        return RoleMapper.map(currentRole);
    }

    @Override
    public void populateDB() {

        Rental rental1 = Rental.builder().name("ABC Car Rentals").internetDomain("www.abccarrentals.com").contactAddress("Camil Ressu 32").owner("Alice B").logoType("logo.png").build();
        rentalRepository.save(rental1);
        rental1.setId(1);

        Branch branch1 = Branch.builder().name("B1").address("Branch 1 address").rental(rental1).build();
        branchRepository.save(branch1);

        Branch branch2 = Branch.builder().name("B2").address("Branch 2 address").rental(rental1).build();
        branchRepository.save(branch2);

        Branch branch3 = Branch.builder().name("B3").address("Branch 3 address").rental(rental1).build();
        branchRepository.save(branch3);

        Branch branch4 = Branch.builder().name("B4").address("Branch 4 address").rental(rental1).build();
        branchRepository.save(branch4);

        Branch branch5 = Branch.builder().name("B5").address("Branch 5 address").rental(rental1).build();
        branchRepository.save(branch5);

        Car car1 = Car.builder().brand("Toyota").model("Camry").bodyType("Sedan").year(2023).color("Silver").mileage(10000.0).status(Status.AVAILABLE).amount(50.0).build();
        carRepository.save(car1);

        Car car2 = Car.builder().brand("Ford").model("Escape").bodyType("SUV").year(2022).color("Blue").mileage(20000.0).status(Status.BOOKED).amount(75.0).build();
        carRepository.save(car2);

        Car car3 = Car.builder().brand("Cupra").model("Noua").bodyType("Break").year(2025).color("Alb").mileage(44300.0).status(Status.AVAILABLE).amount(177.0).build();
        carRepository.save(car3);

        User user1 = User.builder().firstName("John").lastName("Doe").email("example@example.com").address("Calea Victoriei 54").age(29).phoneNumber("1234567898").build();
        userRepository.save(user1);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder().firstName("a").lastName("a").email("a@a.com").address("aaaa").age(29).phoneNumber("1234567898").username("a").password(passwordEncoder.encode("admin")).build();
        userRepository.save(user);

        User user2 = User.builder().firstName("Jane").lastName("Smith").email("example@example.com").address("Camil Ressu 32").age(30).phoneNumber("1234567897").build();
        userRepository.save(user2);

        Reservation reservation1 = Reservation.builder().dateOfBooking(new Date()).car(car1).dateFrom(new Date()).dateTo(LocalDateTime.now().plusDays(10)).returnDepartment("Branch 1").amount(150.0).build();
        reservationRepository.save(reservation1);

        Role role1 = Role.builder().name("Admin").build();
        roleRepository.save(role1);

        Role role2 = Role.builder().name("Angajat").build();
        roleRepository.save(role2);

        Role role3 = Role.builder().name("Client").build();
        roleRepository.save(role3);

        Role role4 = Role.builder().name("VIP").build();
        roleRepository.save(role4);
    }
}
