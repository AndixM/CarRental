package com.example.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @Column(name = "date_of_booking")
    private Date dateOfBooking;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private LocalDateTime dateTo;

    @Column(name = "return_department")
    private String returnDepartment;

    @Column(name = "amount")
    private Double amount;

    @OneToOne
    @JoinColumn(name = "car")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;




}
