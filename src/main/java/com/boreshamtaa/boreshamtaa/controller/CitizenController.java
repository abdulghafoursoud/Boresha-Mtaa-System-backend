package com.boreshamtaa.boreshamtaa.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boreshamtaa.boreshamtaa.model.Citizen;
import com.boreshamtaa.boreshamtaa.service.CitizenService;
// import java.util.Base64;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    private final CitizenService citizenService;

    @Autowired
    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping("/check-zan-id/{zanId}")
    public boolean checkZanIdExists(@PathVariable String zanId) {
        return citizenService.doesZanIdExist(zanId);
    }

// get citizen by zanid

@GetMapping("/get_by_zanid/{zanId}")
public ResponseEntity<Citizen> getCitizenByZanId(@PathVariable String zanId) {
    Optional<Citizen> citizen = citizenService.findByZanId(zanId);
    return citizen.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
}



    // LOGIN
 @PostMapping("/login")
    public ResponseEntity<Map<String, Boolean>> loginCitizen(@RequestBody Map<String, String> loginData) {
        String zanId = loginData.get("zanId");
        String password = loginData.get("password");

        boolean exists = citizenService.existsByZanIdAndPassword(zanId, password);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(response);
    }
//count all citizens
 @GetMapping("/count")
    public Map<String, Long> countAllCitizens() {
        long count = citizenService.countAllCitizens();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return response;
    }
// fetch using ward
@GetMapping("/ward")
    public ResponseEntity<List<Citizen>> getCitizensByWard(@RequestParam String ward) {
        List<Citizen> citizens = citizenService.getCitizensByWard(ward);
        if (citizens.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citizens);
    }




// INSERT INTO DATABASE WITH IMAGE FILE
    @PostMapping
    public ResponseEntity<Citizen> uploadCitizen(
            @RequestParam(value = "profile", required = false) MultipartFile profile,
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String zanId,
            @RequestParam int age,
            @RequestParam String phoneNumber,
            @RequestParam String houseNo,
            @RequestParam String ward,
            @RequestParam String gender,
            @RequestParam String password,
            @RequestParam String birthDate) throws IOException {

        Citizen citizen = new Citizen();
        citizen.setName(name);
        citizen.setAddress(address);
        citizen.setZanId(zanId);
        citizen.setAge(age);
        citizen.setPhoneNumber(phoneNumber);
        citizen.setHouseNo(houseNo);
        citizen.setWard(ward);
        citizen.setGender(gender);
        citizen.setPassword(password);
        citizen.setBirthDate(LocalDate.parse(birthDate));

        if (profile != null && !profile.isEmpty()) {
    // Store the filename instead of the byte array
    citizen.setProfileFileName(profile.getOriginalFilename());
    citizen.setProfile(profile.getBytes());
}
        Citizen savedCitizen = citizenService.saveCitizen(citizen);
        return ResponseEntity.ok(savedCitizen);
    }


    
@GetMapping
public List<Citizen> getAllCitizens() {
    List<Citizen> citizens = citizenService.getAllCitizens();
    return citizens; // Now returns the filename in profileFileName
}

@GetMapping("/{id}")
public ResponseEntity<Citizen> getCitizenById(@PathVariable Long id) {
    return citizenService.getCitizenById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}


// Update citizen password
   @PutMapping("/update_password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody Citizen citizen) {
        Citizen updated = citizenService.updatePassword(id, citizen.getPassword());
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
    // count by ward
@GetMapping("/count-by-ward")
    public long countCitizensByWard(@RequestParam String ward) {
        return citizenService.countCitizensByWard(ward);
    }
    
    @PutMapping("/update/{id}")
public ResponseEntity<?> updateCitizen(
        @PathVariable Long id,
        @RequestParam String address,
        @RequestParam String password,
        @RequestParam Integer age,
        @RequestParam String ward,
        @RequestParam String houseNo,
        @RequestParam(value = "profile", required = false) MultipartFile profile) {

    Optional<Citizen> optionalCitizen = citizenService.findById(id);
    if (!optionalCitizen.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    Citizen citizen = optionalCitizen.get();
    citizen.setAddress(address);
    citizen.setPassword(password); // Consider hashing in production
    citizen.setAge(age);
    citizen.setWard(ward);
    citizen.setHouseNo(houseNo);

    if (profile != null && !profile.isEmpty()) {
        try {
            citizen.setProfile(profile.getBytes());
            citizen.setProfileFileName(profile.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to read profile picture");
        }
    }

    Citizen updatedCitizen = citizenService.saveCitizen(citizen);

    return ResponseEntity.ok(updatedCitizen);
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCitizen(@PathVariable Long id) {
        if (citizenService.existsById(id)) {
            citizenService.deleteCitizen(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}