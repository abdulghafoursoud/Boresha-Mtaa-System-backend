package com.boreshamtaa.boreshamtaa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.boreshamtaa.boreshamtaa.model.WardOfficer;
// import java.util.Optional;

public interface WardOfficerRepository extends JpaRepository<WardOfficer, Long> {
    // Optional<WardOfficer> findByPostcode(int postcode);
     boolean existsByPostcode(String postcode);
// fetch using postcode 
     List<WardOfficer> findByPostcodeContainingIgnoreCase(String postcode);
     //login
     WardOfficer findByPostcodeAndPassword(String postcode, String password);

     public Optional<WardOfficer> findByPostcode(String postcode);
}
