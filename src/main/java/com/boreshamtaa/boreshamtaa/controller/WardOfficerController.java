package com.boreshamtaa.boreshamtaa.controller;

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

import com.boreshamtaa.boreshamtaa.model.WardOfficer;
import com.boreshamtaa.boreshamtaa.service.WardOfficerService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/wardofficers")
public class WardOfficerController {

    private final WardOfficerService wardOfficerService;

    @Autowired
    public WardOfficerController(WardOfficerService wardOfficerService) {
        this.wardOfficerService = wardOfficerService;
    }

  @GetMapping("/check_postcode/{postcode}")
    public ResponseEntity<Map<String, Boolean>> checkPostcode(@PathVariable String postcode) {
        boolean exists = wardOfficerService.existsByPostcode(postcode);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

// fetch using postcode 
@GetMapping("/search")
public ResponseEntity<List<WardOfficer>> searchByPostcode(@RequestParam String postcode) {
    List<WardOfficer> result = wardOfficerService.getWardOfficersByPostcode(postcode);
    if (result.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(result);
}


 // LOGIN
  @PostMapping("/login")
    public Map<String, Boolean> login(@RequestBody Map<String, String> payload) {
        String postcode = payload.get("postcode");
        String password = payload.get("password");

        boolean exists = wardOfficerService.validateWardOfficer(postcode, password);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }

    // Register new ward officer
    @PostMapping
    public ResponseEntity<?> registerWardOfficer(@RequestBody WardOfficer officer) {
        // Check if postcode already exists
        if (wardOfficerService.existsByPostcode(officer.getPostcode())) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Ward Officer already exists with this postcode.");
            return ResponseEntity.badRequest().body(error);
        }

        WardOfficer savedOfficer = wardOfficerService.saveWardOfficer(officer);
        return ResponseEntity.ok(savedOfficer);
    }

// get officer by postcode

@GetMapping("/get_by_postcode/{postcode}")
public ResponseEntity<WardOfficer> getWardOfficerByPostcode(@PathVariable String postcode) {
    Optional<WardOfficer> wardOfficer = wardOfficerService.findByPostcode(postcode);
    return wardOfficer.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
}
    
    // Get all Admins
    @GetMapping
    public List<WardOfficer> getAllWardOfficers() {
        return wardOfficerService.getAllWardOfficers();
    }

    // Get Admin by ID
    @GetMapping("/{id}")
    public ResponseEntity<WardOfficer> getWardOfficerById(@PathVariable Long id) {
        return wardOfficerService.getWardOfficerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//count all ward officers
 @GetMapping("/count")
    public Map<String, Long> countAllWardOfficers() {
        long count = wardOfficerService.countAllWardOfficers();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return response;
    }


    // Update officer password
   @PutMapping("/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody WardOfficer officer) {
        WardOfficer updated = wardOfficerService.updatePassword(id, officer.getPassword());
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Delete Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWardOfficer(@PathVariable Long id) {
        if (wardOfficerService.existsById(id)) {
            wardOfficerService.deleteWardOfficer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
