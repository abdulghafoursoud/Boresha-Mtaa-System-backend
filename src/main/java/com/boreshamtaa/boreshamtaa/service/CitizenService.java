package com.boreshamtaa.boreshamtaa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boreshamtaa.boreshamtaa.model.Citizen;
import com.boreshamtaa.boreshamtaa.repository.CitizenRepository;
import com.boreshamtaa.boreshamtaa.repository.ReportRepository;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;
    private final ReportRepository reportRepository;


    @Autowired
    public CitizenService(CitizenRepository citizenRepository, ReportRepository reportRepository) {
        this.citizenRepository = citizenRepository;
        this.reportRepository = reportRepository;
    }

    
    // check citizen existense using zanid
    public boolean doesZanIdExist(String zanId) {
        return citizenRepository.existsByZanId(zanId);
    }
    // fetch by zanid
public Optional<Citizen> findByZanId(String zanId) {
    return citizenRepository.findByZanId(zanId);
}
// fetch by ward
 public List<Citizen> getCitizensByWard(String ward) {
        return citizenRepository.findByWardIgnoreCase(ward);
    }

    // count by ward
public long countCitizensByWard(String ward) {
        return citizenRepository.countByWard(ward);
    }

// update password by officer
public Citizen updatePassword(Long id, String newPassword) {
        Optional<Citizen> optionalCitizen = citizenRepository.findById(id);
        if (!optionalCitizen.isPresent()) {
            return null;
        }
        Citizen citizen = optionalCitizen.get();
        citizen.setPassword(newPassword);
        return citizenRepository.save(citizen);
    }



public Optional<Citizen> findById(Long id) {
    return citizenRepository.findById(id);
}
    // Create or Update Citizen
    public Citizen saveCitizen(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    // METHOD FOR LOGIN
public boolean existsByZanIdAndPassword(String zanId, String password) {
        return citizenRepository.existsByZanIdAndPassword(zanId, password);
    }

    //count all citizens
public long countAllCitizens() {
        return citizenRepository.count();
    }


    // Get All Citizens
    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }

    // Get Citizen by ID
    public Optional<Citizen> getCitizenById(Long id) {
        return citizenRepository.findById(id);
    }

    // Delete Citizen by ID
     @Transactional
public void deleteCitizen(Long id) {
    Optional<Citizen> citizenOpt = citizenRepository.findById(id);
    if (citizenOpt.isPresent()) {
        String zanId = citizenOpt.get().getZanId();
        reportRepository.deleteByZanId(zanId);
        citizenRepository.deleteById(id);
    }
}


    // Check if Citizen Exists by ID
    public boolean existsById(Long id) {
        return citizenRepository.existsById(id);
    }
    
}
