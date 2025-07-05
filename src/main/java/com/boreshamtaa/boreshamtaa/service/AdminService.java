package com.boreshamtaa.boreshamtaa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boreshamtaa.boreshamtaa.model.Admin;
import com.boreshamtaa.boreshamtaa.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

     // fetch by email
public Optional<Admin> findByEmail(String email) {
    return adminRepository.findByEmail(email);
}

// update admin password
public void updatePassword(Long id, String newPassword) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setPassword(newPassword);

        adminRepository.save(admin);
    }

    // Create or Update Admin
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Get All Admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get Admin by ID
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    // Delete Admin by ID
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    // Check if Admin Exists by ID
    public boolean existsById(Long id) {
        return adminRepository.existsById(id);
    }


    // METHOD FOR LOGIN
public boolean validateAdmin(String email, String password) {
        Admin admin = adminRepository.findByEmailAndPassword(email, password);
        return admin != null;
    }
}
