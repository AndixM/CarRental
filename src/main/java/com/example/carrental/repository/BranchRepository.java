package com.example.carrental.repository;

import com.example.carrental.entity.Branch;
import com.example.carrental.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    @Query("SELECT b FROM Branch b WHERE b.name = :branchName")
    Optional<Branch> findByBranchName(String branchName);
}
