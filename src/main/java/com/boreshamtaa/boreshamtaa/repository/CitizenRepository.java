package com.boreshamtaa.boreshamtaa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boreshamtaa.boreshamtaa.model.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
   // check zan id existence
    boolean existsByZanId(String zanId);
    //login
    boolean existsByZanIdAndPassword(String zanId, String password);
// fetch
    public Optional<Citizen> findByZanId(String zanId);

    // fetch by ward 
    List<Citizen> findByWardIgnoreCase(String ward);

    // count using ward
    long countByWard(String ward);
}
