package com.boreshamtaa.boreshamtaa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boreshamtaa.boreshamtaa.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmailAndPassword(String email, String password);
    public Optional<Admin> findByEmail(String email);
}
