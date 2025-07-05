package com.boreshamtaa.boreshamtaa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boreshamtaa.boreshamtaa.model.WardOfficer;
import com.boreshamtaa.boreshamtaa.repository.WardOfficerRepository;

@Service
public class WardOfficerService {

    private final WardOfficerRepository wardOfficerRepository;

    @Autowired
    public WardOfficerService(WardOfficerRepository wardOfficerRepository) {
        this.wardOfficerRepository = wardOfficerRepository;
    }
// update password
public WardOfficer updatePassword(Long id, String newPassword) {
        Optional<WardOfficer> optionalOfficer = wardOfficerRepository.findById(id);
        if (!optionalOfficer.isPresent()) {
            return null;
        }
        WardOfficer officer = optionalOfficer.get();
        officer.setPassword(newPassword);
        return wardOfficerRepository.save(officer);
    }
// fetch by postcode
public List<WardOfficer> getWardOfficersByPostcode(String postcode) {
    return wardOfficerRepository.findByPostcodeContainingIgnoreCase(postcode);
}

   public boolean existsByPostcode(String postcode) {
    return wardOfficerRepository.existsByPostcode(postcode);
}

public WardOfficer saveWardOfficer(WardOfficer wardOfficer) {
    return wardOfficerRepository.save(wardOfficer);
}

    // Get All Ward Officers
    public List<WardOfficer> getAllWardOfficers() {
        return wardOfficerRepository.findAll();
    }

 // METHOD FOR LOGIN
public boolean validateWardOfficer(String postcode, String password) {
        WardOfficer wardOfficer = wardOfficerRepository.findByPostcodeAndPassword(postcode, password);
        return wardOfficer != null;
    }
// fetch by postcode
public Optional<WardOfficer> findByPostcode(String postcode) {
    return wardOfficerRepository.findByPostcode(postcode);
}

    // Get Ward Officer by ID
    public Optional<WardOfficer> getWardOfficerById(Long id) {
        return wardOfficerRepository.findById(id);
    }

    // Delete Ward Officer by ID
    public void deleteWardOfficer(Long id) {
        wardOfficerRepository.deleteById(id);
    }

    // Count all Ward Officers
    public long countAllWardOfficers() {
        return wardOfficerRepository.count();
    }

    // Check if Ward Officer Exists by ID
    public boolean existsById(Long id) {
        return wardOfficerRepository.existsById(id);
    }
}
